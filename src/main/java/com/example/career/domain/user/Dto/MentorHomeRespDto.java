package com.example.career.domain.user.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MentorHomeRespDto {
    Long id;
    String nickname;
    String role;
    int status;
    String profileImg;

}
