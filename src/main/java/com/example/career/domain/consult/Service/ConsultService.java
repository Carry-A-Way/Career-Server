package com.example.career.domain.consult.Service;

import com.example.career.domain.consult.Dto.*;
import com.example.career.domain.user.Entity.User;

import java.util.List;

public interface ConsultService {
    public List<UpcomingConsults> getList(User mentor, int status);
    public MentorHomeRespDto getMentorHome(User mentor);
    public ConsultEachRespDto requestConsult(ConsultYesorNoReqDto consultYesorNoReqDto, int status);
    public void mentorJoinInConsult(Long consultId);
}
