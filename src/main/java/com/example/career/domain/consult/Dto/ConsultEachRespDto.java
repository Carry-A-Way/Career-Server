package com.example.career.domain.consult.Dto;

import com.example.career.domain.user.Entity.StudentDetail;
import com.example.career.domain.user.Entity.TutorDetail;
import com.example.career.domain.user.Entity.User;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConsultEachRespDto {
    private Long id;

    private Long reviewId;

    private String contentsUrl;

    private String zoomLink;

    private int status;
    private String reason;
    private User mentor;

    private User mentee;

    private String major;

    private LocalDateTime studentEnter;

    private LocalDateTime studentLeft;

    private LocalDateTime tutorEnter;

    private LocalDateTime tutorLeft;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
