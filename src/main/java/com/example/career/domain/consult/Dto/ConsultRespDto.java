package com.example.career.domain.consult.Dto;

import com.example.career.domain.user.Dto.MenteeRespDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsultRespDto {
    private Long consultId;
    private String zoomLink;
    private String major;
    private int status;
    private MenteeRespDto menteeRespDto;
    private String flow;
    private String questions;

}
