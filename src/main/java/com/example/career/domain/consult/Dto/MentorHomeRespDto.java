package com.example.career.domain.consult.Dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MentorHomeRespDto {
    private List<LastUpcomingConsult> lastUpcomingConsult;
    private List<UpcomingConsults> upcomingConsult;
    private List<PreviousConsult> previousConsult;
    private List<CanceledConsultByMentor> canceledConsultByMentor;
    private List<CanceledConsultByMentee> canceledConsultByMentee;
}
