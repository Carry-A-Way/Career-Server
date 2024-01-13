package com.example.career.domain.community.Dto.Brief;

import com.example.career.domain.user.Entity.TutorDetail;
import com.example.career.domain.user.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBriefWithRate {
    private Long id;
    private String nickname;
    private Boolean isTutor;
    private String profileImg;
    private int rateCount;
    private float rateAvg;

    public UserBriefWithRate(User user, TutorDetail tutorDetail) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.isTutor = user.getIsTutor();
        this.profileImg = user.getProfileImg();
        this.rateCount = tutorDetail.getRateCount();
        this.rateAvg = tutorDetail.getRateAvg();
    }
}