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
public class ReviewRespDto {
    private Long id;
    private Long consultId;
    private int rate;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
