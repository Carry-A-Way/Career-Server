package com.example.career.domain.consult.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConsultMentorRespDto {
    private Long mentorId;
    private String nickname;
    private boolean gender;
    private String birth;
    private String profileImg;
    private String mentorUrl;
}
