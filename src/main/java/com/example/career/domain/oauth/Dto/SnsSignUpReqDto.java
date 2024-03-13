package com.example.career.domain.oauth.Dto;

import com.example.career.domain.user.Dto.SignUpReqDto;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class SnsSignUpReqDto {
    private String name; //
    private String username; //
    private String birth;
    private String nickname;//
    private String telephone; //
    private String role ="USER"; // USER
    private Boolean gender; //
    private String email;
    private Boolean isTutor;

    // sns related fields
    private Long snsId;
    public SignUpReqDto toSignUpReqDto() {
        return SignUpReqDto.builder()
                .name(name)
                .username(username)
                .password(Long.toString(snsId))
                .birth(birth)
                .nickname(nickname)
                .telephone(telephone)
                .role(role)
                .gender(gender)
                .email(email)
                .isTutor(isTutor)
                .build();
    }
}
