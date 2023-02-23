package com.example.career.domain.user.Dto;

import com.example.career.domain.user.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpReqDto {
    private String name;
    private String username;
    private String telephone;
    private String password;
    private Boolean gender;
    private String nickname;

    public User toUserEntity(){
        return User.builder().name(name)
                .username(username)
                .nickname(nickname)
                .password(password)
                .telephone(telephone)
                .gender(gender)
                .role("USER")
                .authType(1)
                .build();
    }
}
