package com.example.career.domain.community.Dto.Brief;

import com.example.career.domain.user.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class UserBrief {
    private Long id;
    private String nickname;
    private Boolean isTutor;
    private String profileImg;

    public UserBrief(User user) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.isTutor = user.getIsTutor();
        this.profileImg = user.getProfileImg();
    }
}