package com.example.career.domain.consult.Service;

import com.example.career.domain.consult.Dto.*;
import com.example.career.domain.consult.Entity.Consult;
import com.example.career.domain.consult.Repository.ConsultRepository;
import com.example.career.domain.user.Entity.User;
import com.example.career.domain.user.Repository.StudentDetailRepository;
import com.example.career.domain.user.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConsultServiceImpl implements ConsultService{
// 0: 상담 수락 전, 1: 상담 전, 2: 상담 종료, 3:상담 거절
    private final ConsultRepository consultRepository;
    @Override
    public List<UpcomingConsults> getList(User mentor, int status) {
        List<Consult> consultList = consultRepository.findAllByMentorAndStatus(mentor, status);
        List<UpcomingConsults> upcomingConsults = new ArrayList<>();
        for(Consult consult : consultList) {
            UpcomingConsults up = consult.toUpcomingConsult();
            // 학생 정보
            up.setStudent(consult.getMentee().toConsultMenteeRespDto());
            upcomingConsults.add(up);
        }

        return upcomingConsults;
    }

    // 멘토 상담내역 메서드
    @Override
    public MentorHomeRespDto getMentorHome(User mentor) {
        MentorHomeRespDto mentorHomeRespDto = new MentorHomeRespDto();
        List<Consult> mentorConsultList = consultRepository.findAllByMentor(mentor);
        if(mentorConsultList == null & !mentor.getIsTutor()) return null;
        List<LastUpcomingConsult> lastUpcomingConsults = new ArrayList<>();
        List<UpcomingConsults> upcomingConsults = new ArrayList<>();
        List<PreviousConsult> previousConsults = new ArrayList<>();
        for(Consult consult : mentorConsultList) {
            System.out.println(consult);
            // 예정 상담일 때
            if(consult.getStatus() == 0) {
                //상담 내용
                LastUpcomingConsult lastUp = consult.toLastUpcomingConsult();
                // 학생 정보
                lastUp.setStudent(consult.getMentee().toConsultMenteeRespDto());
                lastUpcomingConsults.add(lastUp);
            }
            // 진행 상담일 때
            if(consult.getStatus() == 1) {
                //상담 내용
                UpcomingConsults up = consult.toUpcomingConsult();
                // 학생 정보
                up.setStudent(consult.getMentee().toConsultMenteeRespDto());
                upcomingConsults.add(up);
            }
            // 완료 상담일 때 + 취소된 상담
            if(consult.getStatus() == 2 || consult.getStatus() == 3) {
                //상담 내용
                PreviousConsult pre = consult.toPreviousConsult();
                // 학생 정보
                pre.setStudent(consult.getMentee().toConsultMenteeRespDto());
                previousConsults.add(pre);
            }
        }

        mentorHomeRespDto.setLastUpcomingConsult(lastUpcomingConsults);
        mentorHomeRespDto.setUpcomingConsult(upcomingConsults);
        mentorHomeRespDto.setPreviousConsult(previousConsults);

        return mentorHomeRespDto;
    }

    @Override
    public ConsultEachRespDto requestConsult(ConsultYesorNoReqDto consultYesorNoReqDto, int status) {
        Consult consult;
        try{
            consult = consultRepository.findById(consultYesorNoReqDto.getConsultId()).get();

        }catch(Exception e) {
            return null;
        }

        if(status==1) { // 수락
            consult.setStatus(1);
            consult.setReason("");
        }else if(status == 0){
            consult.setStatus(4);
            consult.setReason(consultYesorNoReqDto.getReason());
        }else {
            return null;
        }

        consultRepository.save(consult);

        //Entity -> Save -> Dto
        return consultRepository.save(consult).toConsultEachRespDto();
    }
}
