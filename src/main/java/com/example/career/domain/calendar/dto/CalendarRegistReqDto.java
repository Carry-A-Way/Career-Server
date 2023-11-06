package com.example.career.domain.calendar.dto;

import com.example.career.domain.consult.Entity.Consult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalendarRegistReqDto {
    private Long menteeId;
    private Long mentorId;
    private String major;
    private String flow;
    private String questions;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    public Consult toEntityConsult() {
        return Consult
                .builder()
                .major(major)
                .startTime(startTime)
                .endTime(endTime)
                .flow(flow)
                .questions(questions)
                .status(0)
                .build();
    }
}
