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
    private String consultMajor1;
    private String consultMajor2;
    private String consultMajor3;
    private String consultMethod;

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
        return TutorDetail.builder().tutorId(id)
                .consultMajor1(consultMajor1)
                .consultMajor2(consultMajor2)
                .consultMajor3(consultMajor3)
                .cash(0)
                .consultMethod(consultMethod)
                .build();
    }
}
