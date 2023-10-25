package com.example.career.domain.calendar.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CalendarMentorPossibleReqDto {
    private LocalDateTime start;
    private LocalDateTime end;
}
