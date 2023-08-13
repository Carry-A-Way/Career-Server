package com.example.career.domain.user.Dto;

import com.example.career.domain.user.Entity.Authority;
import com.example.career.domain.user.Entity.User;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

import java.util.Set;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class SignUpReqDto {
    private String name; //
    private String username; //
    private String telephone; //
    private String role ="USER"; // USER
    private String introduce; //
    private String password; //
    private Boolean gender; //
    private String nickname;//
    private String birth;
    private Set<AuthorityDto> authorityDtoSet;

    private String profileImg; // MultipartFile
    private String consultMajor1;
    private String consultMajor2;
    private String consultMajor3;

    private String plan; // 커리어 모ㅗㄱㄱ표

    private String hobby;

    private List<SchoolDto> schoolList;

    private List<CareerDto> careerList;

    private List<TagDto> tagList;

    private List<MultipartFile> activeImg;


    public User toUserEntity(Set<Authority> authorities){

        return User.builder()
                .password(password)
                .name(name)
                .profileImg(profileImg)
                .username(username)
                .nickname(nickname)
                .telephone(telephone)
                .gender(gender)
                .birth(birth)
                .role(role)
                .status(0)
                .introduce(introduce)
                .authType(1)
                .authorities(authorities)
                .activated(true)
                .build();
    }

    public static SignUpReqDto from(User user) {
        if(user == null) return null;

        return SignUpReqDto.builder()
                .name(user.getName())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .password(user.getPassword())
                .gender(user.getGender())
                .birth(user.getBirth())
                .telephone(user.getTelephone())
                .authorityDtoSet(user.getAuthorities().stream()
                        .map(authority -> AuthorityDto.builder().authorityName(authority.getAuthorityName()).build())
                        .collect(Collectors.toSet()))
                .build();
    }
}
