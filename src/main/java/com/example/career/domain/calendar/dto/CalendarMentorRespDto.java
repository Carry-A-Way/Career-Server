package com.example.career.domain.calendar.dto;

import com.example.career.domain.consult.Dto.LastUpcomingConsult;
import com.example.career.domain.consult.Dto.PreviousConsult;
import com.example.career.domain.consult.Dto.UpcomingConsults;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CalendarMentorRespDto {
    // 지난 상담은 필요없음 !
    private List<LastUpcomingConsult> lastUpcomingConsult;
    private List<UpcomingConsults> upcomingConsult;
}
