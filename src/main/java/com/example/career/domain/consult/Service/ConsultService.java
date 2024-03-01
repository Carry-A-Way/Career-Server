package com.example.career.domain.consult.Service;

import com.example.career.domain.community.Dto.Brief.UserBriefWithRate;
import com.example.career.domain.consult.Dto.*;
import com.example.career.domain.consult.Entity.Review;
import com.example.career.domain.user.Entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ConsultService {
    public List<UpcomingConsults> getList(User mentor, int status);
    public MentorHomeRespDto getMentorHome(User mentor);
    public MentorHomeRespDto getMenteeHome(User mentee);
    public ConsultEachRespDto requestConsult(ConsultYesorNoReqDto consultYesorNoReqDto, int status);
    public void mentorJoinInConsult(Long consultId);
    public ReviewRespDto writeReview(User user, ReviewWriteReqDto reviewWriteReqDto);
    public ReviewRespDto updateReview(User user, ReviewWriteReqDto reviewWriteReqDto);
    public ResponseEntity<String> updateConsultationStatus();

    public ResponseEntity<?> menteeScheduledConsultList(User user);
}
