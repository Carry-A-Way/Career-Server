package com.example.career.domain.consult.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BriefConsultRespDto {
    private Long id;
    private String zoomLink;
    private int status;
    private String questions;
    private String flow;
    private String major;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

}
