package com.example.career.domain.user.Dto;

import com.example.career.domain.user.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;


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
    private Set<AuthorityDto> authorityDtoSet;

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

    public static SignUpReqDto from(User user) {
        if(user == null) return null;

        return SignUpReqDto.builder()
                .username(user.getUsername())
                .nickname(user.getNickname())
                .password(user.getPassword())
                .gender(user.getGender())
                .telephone(user.getTelephone())
                .authorityDtoSet(user.getAuthorities().stream()
                        .map(authority -> AuthorityDto.builder().authorityName(authority.getAuthorityName()).build())
                        .collect(Collectors.toSet()))
                .build();
    }
}
