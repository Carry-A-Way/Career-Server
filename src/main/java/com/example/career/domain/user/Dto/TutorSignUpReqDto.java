package com.example.career.domain.user.Dto;

import com.example.career.domain.user.Entity.TutorDetail;
import com.example.career.domain.user.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TutorSignUpReqDto {
    private Long id;
    private String name;
    private String username;
    private String telephone;
    private String role;
    private String introduce;
    private String password;
    private Boolean gender;
    private String nickname;
    private String consultingMajor1;
    private String consultingMajor2;
    private String consultingMajor3;
    private String consultingMethod;

    public User toUserEntity(){
        return User.builder().name(name)
                .username(username)
                .nickname(nickname)
                .password(password)
                .telephone(telephone)
                .gender(gender)
                .role(role)
                .status(1)
                .introduce(introduce)
                .authType(1)
                .build();
    }
    public TutorDetail toTutorDetailEntity(){
        return TutorDetail.builder().tutor_id(id)
                .consultingMajor1(consultingMajor1)
                .consultingMajor2(consultingMajor2)
                .consultingMajor3(consultingMajor3)
                .cash(0)
                .consultingMethod(consultingMethod)
                .build();
    }
}
