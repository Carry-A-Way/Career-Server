package com.example.career.domain.calendar.service;

import com.example.career.domain.calendar.dto.CalendarMentorRespDto;
import com.example.career.domain.consult.Dto.*;
import com.example.career.domain.consult.Entity.Consult;
import com.example.career.domain.consult.Repository.ConsultRepository;
import com.example.career.domain.consult.Repository.QueryRepository;
import com.example.career.domain.meeting.dto.ZoomMeetingObjectDTO;
import com.example.career.domain.meeting.entity.ZoomMeetingObjectEntity;
import com.example.career.domain.meeting.service.ZoomMeetingService;
import com.example.career.domain.meeting.service.ZoomMeetingServiceImpl;
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
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalendarServiceImpl implements CalendarService{
//    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("Consult");
    private final ConsultRepository consultRepository;
    private final QueryRepository queryRepository;
    private final UserRepository userRepository;
    private final ZoomMeetingService zoomMeetingService;
    @Override
    public CalendarMentorRespDto getMentorCalendar(Long id) {
        CalendarMentorRespDto calendarMentorRespDto = new CalendarMentorRespDto();
        List<Consult> mentorConsultList = consultRepository.findAllByTutorId(id);
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
                lastUp.setStudent(userRepository.findById(consult.getStuId()).get().toConsultMenteeRespDto());
                // 학생의 요구(질문) 사항
                lastUp.setStudentRequest(queryRepository.findByConsultId(consult.getId()).toQueryRespDto());
                lastUpcomingConsults.add(lastUp);
            }
            // 진행 상담일 때
            if(consult.getStatus() == 1) {
                //상담 내용
                UpcomingConsults up = consult.toUpcomingConsult();
                // 학생 정보
                up.setStudent(userRepository.findById(consult.getStuId()).get().toConsultMenteeRespDto());
                // 학생의 요구(질문) 사항
                up.setStudentRequest(queryRepository.findByConsultId(consult.getId()).toQueryRespDto());
                upcomingConsults.add(up);
            }

        }

        calendarMentorRespDto.setLastUpcomingConsult(lastUpcomingConsults);
        calendarMentorRespDto.setUpcomingConsult(upcomingConsults);

        return calendarMentorRespDto;
    }

    @Override
    @Transactional
    public Consult denyConsultByMentor(CalendarDenyReqDto calendarDenyReqDto) {

        Consult consult = consultRepository.findById(calendarDenyReqDto.getConsultId()).get();

        // 멘토가 자신의 consult를 삭제하는지 체크
        if(consult.getTutorId() == calendarDenyReqDto.getId()) {

            // 준영속 변경감지
            consult.setStatus(3);
            consult.setReason(calendarDenyReqDto.getReason());

            return consult;
        }
        return null;
    }

    @Override
    @Transactional
    public Consult AcceptConsultByMentor(CalendarDenyReqDto calendarDenyReqDto, String username) throws IOException {
        Consult consult = consultRepository.findById(calendarDenyReqDto.getConsultId()).get();

        // 멘토가 자신의 consult를 수락 & 상담 신청 중(status = 0) 인 거 체크
        if(consult.getTutorId() == calendarDenyReqDto.getId() && consult.getStatus() == 0) {

            // 준영속 변경감지
            consult.setStatus(1);

            ZoomMeetingObjectDTO zoomMeetingObjectDTO = new ZoomMeetingObjectDTO();
            Duration duration = Duration.between(consult.getEndTime(), consult.getStartTime());
            long minutes = duration.toMinutes();

            zoomMeetingObjectDTO.setStart_time(consult.getStartTime()+"");
            zoomMeetingObjectDTO.setDuration((int)minutes);
            zoomMeetingObjectDTO.setTopic(username+"님의"+consult.getMajor()+" 상담입니다.");
            zoomMeetingObjectDTO.setAgenda(username+"님의"+consult.getMajor()+" 상담입니다.");

            ZoomMeetingObjectEntity zoomMeetingObjectEntity = zoomMeetingService.createMeeting(zoomMeetingObjectDTO);

            consult.setZoomLink(zoomMeetingObjectEntity.getJoin_url());
            consult.setMeetingId(zoomMeetingObjectEntity.getId());
            return consult;
        }
        return null;
    }
}
