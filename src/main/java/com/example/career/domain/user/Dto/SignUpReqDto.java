package com.example.career.domain.user.Dto;

import com.example.career.domain.user.Entity.TutorDetail;
import com.example.career.domain.user.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import java.util.Set;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    private List<School> shoolList;

    private List<Career> careerList;

    private List<Tag> tagList;

    private List<String> activeImg;

    public User toUserEntity(){
        return User.builder().name(name)
                .username(username)
                .nickname(nickname)
                .password(password)
                .telephone(telephone)
                .gender(gender)
                .birth(birth)
                .role(role)
                .status(0)
                .introduce(introduce)
                .authType(1)
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
