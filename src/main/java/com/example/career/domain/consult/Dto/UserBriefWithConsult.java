package com.example.career.domain.consult.Dto;

import com.example.career.domain.user.Entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserBriefWithConsult {
    private Long id;
    private String name;
    private String nickname;
    private Boolean isTutor;
    private String profileImg;
    private String introduce;
    private int rateCount;
    private float rateAvg;
    private String birth;
    private String consultMajor1;

    private String consultMajor2;

    private String consultMajor3;
    private String consultMethod;

    private BriefConsultRespDto briefConsultRespDto;
    private Boolean heart;
    private int wage;

    public UserBriefWithConsult(User user, TutorDetail tutorDetail) {
        this.id = user.getId();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.isTutor = user.getIsTutor();
        this.profileImg = user.getProfileImg();
        this.birth = user.getBirth();
        this.introduce = user.getIntroduce();

        this.rateCount = tutorDetail.getRateCount();
        this.rateAvg = tutorDetail.getRateAvg();
        this.consultMajor1 = tutorDetail.getConsultMajor1();
        this.consultMajor2 = tutorDetail.getConsultMajor2();
        this.consultMajor3 = tutorDetail.getConsultMajor3();
        this.consultMethod = tutorDetail.getConsultMethod();
        this.wage = tutorDetail.getWage();
    }
}