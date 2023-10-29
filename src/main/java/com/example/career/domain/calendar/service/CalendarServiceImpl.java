package com.example.career.domain.calendar.service;

import com.example.career.domain.calendar.dto.CalendarGetPossibleTimeRespDto;
import com.example.career.domain.calendar.dto.CalendarMentorPossibleReqDto;
import com.example.career.domain.calendar.dto.CalendarMentorRespDto;
import com.example.career.domain.calendar.dto.CalendarRegistReqDto;
import com.example.career.domain.consult.Dto.*;
import com.example.career.domain.consult.Entity.Consult;
import com.example.career.domain.consult.Entity.TutorSlot;
import com.example.career.domain.consult.Repository.ConsultRepository;
import com.example.career.domain.consult.Repository.QuestionRepository;
import com.example.career.domain.consult.Repository.TutorSlotRepository;
import com.example.career.domain.meeting.dto.ZoomMeetingObjectDTO;
import com.example.career.domain.meeting.entity.ZoomMeetingObjectEntity;
import com.example.career.domain.meeting.service.ZoomMeetingService;
import com.example.career.domain.meeting.service.ZoomMeetingServiceImpl;
import com.example.career.domain.user.Entity.TutorDetail;
import com.example.career.domain.user.Entity.User;
import com.example.career.domain.user.Repository.StudentDetailRepository;
import com.example.career.domain.user.Repository.TutorDetailRepository;
import com.example.career.domain.user.Repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CalendarServiceImpl implements CalendarService{
//    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("Consult");
    private final ConsultRepository consultRepository;
    private final UserRepository userRepository;
    private final ZoomMeetingService zoomMeetingService;
    private final TutorSlotRepository tutorSlotRepository;
    private final TutorDetailRepository tutorDetailRepository;
    @Override
    public CalendarMentorRespDto getMentorCalendar(User mentor) {
        CalendarMentorRespDto calendarMentorRespDto = new CalendarMentorRespDto();
        List<Consult> mentorConsultList = consultRepository.findAllByMentor(mentor);
        if(mentorConsultList == null) return null;
        List<LastUpcomingConsult> lastUpcomingConsults = new ArrayList<>();
        List<UpcomingConsults> upcomingConsults = new ArrayList<>();
        for(Consult consult : mentorConsultList) {
            System.out.println(consult);
            // 예정 상담일 때
            if(consult.getStatus() == 0) {
                //상담 내용
                LastUpcomingConsult lastUp = consult.toLastUpcomingConsult();
                // 학생 정보
                lastUp.setStudent(consult.getMentee().toConsultMenteeRespDto());
                // 학생의 요구(질문) 사항
                lastUp.setStudentRequest(consult.getQuestion().toQueryRespDto());
                lastUpcomingConsults.add(lastUp);
            }
            // 진행 상담일 때
            if(consult.getStatus() == 1) {
                //상담 내용
                UpcomingConsults up = consult.toUpcomingConsult();
                // 학생 정보
                up.setStudent(consult.getMentee().toConsultMenteeRespDto());
                // 학생의 요구(질문) 사항
                up.setStudentRequest(consult.getQuestion().toQueryRespDto());
                upcomingConsults.add(up);
            }

        }

        calendarMentorRespDto.setLastUpcomingConsult(lastUpcomingConsults);
        calendarMentorRespDto.setUpcomingConsult(upcomingConsults);

        return calendarMentorRespDto;
    }

    @Override
    @Transactional
    public Boolean denyConsultByMentor(User user, CalendarDenyReqDto calendarDenyReqDto) {

        Consult consult = consultRepository.findById(calendarDenyReqDto.getConsultId()).get();

        // 멘토가 자신의 consult를 삭제하는지 체크
        if(consult.getMentor().getId() == user.getId()) {

            // 준영속 변경감지
            consult.setStatus(3);
            consult.setReason(calendarDenyReqDto.getReason());

            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public Boolean AcceptConsultByMentor(CalendarDenyReqDto calendarDenyReqDto, User user) throws IOException {
        Consult consult = consultRepository.findById(calendarDenyReqDto.getConsultId()).get();

        // 멘토가 자신의 consult를 수락 & 상담 신청 중(status = 0) 인 거 체크
        if(consult.getMentor().getId() == user.getId() && consult.getStatus() == 0 && consult != null) {

            // 준영속 변경감지
            consult.setStatus(1);

            ZoomMeetingObjectDTO zoomMeetingObjectDTO = new ZoomMeetingObjectDTO();
            Duration duration = Duration.between(consult.getStartTime(), consult.getEndTime());
            long minutes = duration.toMinutes();
            zoomMeetingObjectDTO.setStart_time(consult.getStartTime()+"");
            zoomMeetingObjectDTO.setDuration((int)minutes);
            zoomMeetingObjectDTO.setTopic(user.getNickname()+"님의 "+consult.getMajor()+" 상담입니다.");
            zoomMeetingObjectDTO.setAgenda(user.getNickname()+"님의 "+consult.getMajor()+" 상담입니다.");
            ZoomMeetingObjectEntity zoomMeetingObjectEntity = null;
            try {
                zoomMeetingObjectEntity = zoomMeetingService.createMeeting(zoomMeetingObjectDTO);
            }catch(Exception e) {
                System.out.println("상담 수락 중 ZOOM API 에러\n" +e.getMessage());
                return false;
            }

            consult.setZoomLink(zoomMeetingObjectEntity.getJoin_url());
            consult.setMeetingId(zoomMeetingObjectEntity.getId());
            return true;
        }
        return false;
    }

    @Override
    public Boolean RegisterConsultByMentee(CalendarRegistReqDto calendarRegistReqDto) {
        try{
            Consult consult = calendarRegistReqDto.toEntityConsult();
            consult.setMentor(userRepository.findById(calendarRegistReqDto.getMentorId()).get());
            consult.setMentee(userRepository.findById(calendarRegistReqDto.getMenteeId()).get());
            consult.setQuestion(calendarRegistReqDto.toEntityQuery());

            // consult 저장
            consultRepository.save(consult);


        }catch (Exception e) {
            System.out.println("RegisterConsultByMentee error : "+e);
            return false;
        }
        return true;
    }
    @Transactional
    @Override
    public boolean insertMentorPossibleTime(CalendarMentorPossibleReqDto calendarMentorPossibleReqDto, Long userId) {
        // 시간 -> 바이트 변환
        TimeChanger timeChanger = new TimeChanger();
        byte[] newBytes = timeChanger.dateTimeToByte(calendarMentorPossibleReqDto.getStart(), calendarMentorPossibleReqDto.getEnd());
        LocalDate date = calendarMentorPossibleReqDto.getStart().toLocalDate();
        TutorDetail tutorDetail;

        // 멘토 확인
        try{
            tutorDetail = tutorDetailRepository.findById(userId).get();
        }catch (NoSuchElementException e){
            System.out.println("멘토가 아닙니다.");
            return false;
        }

        // 유저의 날짜에 대한 Slot이 존재하는지 확인
        TutorSlot tutorSlot = tutorSlotRepository.findTutorSlotByTutorDetailAndConsultDate(tutorDetail, date);

        // 새 날짜에 대한 Slot 추가
        if(tutorSlot == null) {
            tutorSlot = new TutorSlot();
            tutorSlot.setTutorDetail(tutorDetail);
            tutorSlot.setConsultDate(date);
            tutorSlot.setPossibleTime(newBytes);
            System.out.println("TTTT"+tutorSlot);
            tutorSlotRepository.save(tutorSlot);
        }else {
            // 기존 Slot에 Time 수정
            byte[] oldBytes = tutorSlot.getPossibleTime();
            byte[] resultBytes = timeChanger.combineBytesWithXOR(newBytes,oldBytes,0);
            if (resultBytes == null) {
                System.out.println("중복된 시간 체크섬 오류");
                return false; // 체크섬 오류
            }
            tutorSlot.setPossibleTime(resultBytes);
        }
        return true;
    }

    @Override
    public CalendarGetPossibleTimeRespDto getMentorPossibleTime(Long userId) {
        TutorDetail tutorDetail;
        // 멘토 확인
        try{
            tutorDetail = tutorDetailRepository.findById(userId).get();
        }catch (NoSuchElementException e){
            System.out.println("멘토가 아닙니다.");
            return null;
        }

        // 유저의 날짜에 대한 Slot이 존재하는지 확인
        List<TutorSlot> tutorSlots = tutorSlotRepository.findAllByTutorDetail(tutorDetail);
        if(tutorSlots == null) {
            System.out.println("가져올 시간이 없습니다.");
            return null;
        }
        CalendarGetPossibleTimeRespDto calendarGetPossibleTimeRespDto = new CalendarGetPossibleTimeRespDto();
        calendarGetPossibleTimeRespDto.setDateList(tutorSlots.stream().map(BitChanger::convertByteArrayToTimeList).toList());
        return calendarGetPossibleTimeRespDto;
    }
    // 상담 가능 시간 삭제
    @Override
    @Transactional
    public boolean deleteMentorPossibleTime(CalendarMentorPossibleReqDto calendarMentorPossibleReqDto,Long userId) {
        TutorDetail tutorDetail;
        // 멘토 확인
        try{
            tutorDetail = tutorDetailRepository.findById(userId).get();
        }catch (NoSuchElementException e){
            System.out.println("멘토가 아닙니다.");
            return false;
        }
        // 유저의 날짜에 대한 Slot이 존재하는지 확인
        TutorSlot tutorSlot = tutorSlotRepository.findTutorSlotByTutorDetailAndConsultDate(tutorDetail, calendarMentorPossibleReqDto.getStart().toLocalDate());
        if(tutorSlot== null) {
            System.out.println("삭제할 시간이 없습니다.");
            return false;
        }
        TimeChanger timeChanger = new TimeChanger();
        byte[] newBytes = timeChanger.dateTimeToByte(calendarMentorPossibleReqDto.getStart(), calendarMentorPossibleReqDto.getEnd());
        // 기존 Slot에 Time 수정
        byte[] oldBytes = tutorSlot.getPossibleTime();
        byte[] resultBytes = timeChanger.combineBytesWithXOR(newBytes,oldBytes,1);
        if (resultBytes == null) {
            System.out.println("중복된 시간 체크섬 오류");
            return false; // 체크섬 오류
        }
        tutorSlot.setPossibleTime(resultBytes);

        return true;
    }
}
