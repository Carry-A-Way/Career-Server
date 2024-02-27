package com.example.career.domain.community.Dto.Brief;

import com.example.career.domain.consult.Dto.ReviewRespDto;
import com.example.career.domain.user.Entity.FAQ;
import com.example.career.domain.user.Entity.School;
import com.example.career.domain.user.Entity.TutorDetail;
import com.example.career.domain.user.Entity.User;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBriefWithRate {
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

    private List<School> schoolList;
    private List<ReviewRespDto> review;
    private List<FAQ> FAQ;

    private Boolean heart;


    public UserBriefWithRate(User user, TutorDetail tutorDetail) {
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
    }
}