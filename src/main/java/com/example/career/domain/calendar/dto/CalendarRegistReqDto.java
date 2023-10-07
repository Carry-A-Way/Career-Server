package com.example.career.domain.calendar.dto;

import com.example.career.domain.consult.Entity.Consult;
import com.example.career.domain.consult.Entity.Query;
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
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private StuRequest stuRequest;

    public Consult toEntityConsult(){
        return Consult
                .builder()
                .tutorId(mentorId)
                .stuId(menteeId)
                .major(major)
                .startTime(startTime)
                .endTime(endTime)
                .status(0)
                .build();
    }
    public Query toEntityQuery() {
        return Query
                .builder()
                .query1(getStuRequest().query1)
                .query2(getStuRequest().query2)
                .flow(getStuRequest().flow)
                .etc(getStuRequest().etc)
                .build();
    }

}
@Data
@AllArgsConstructor
@NoArgsConstructor
class StuRequest {
    String etc;
    String query1;
    String query2;
    String flow;
}
