package com.example.career.domain.user.Dto;

import com.example.career.domain.user.Entity.Career;
import com.example.career.domain.user.Entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CareerDto {
    private Long idx;
    private String careerType;
    private String careerName;
    private String startDate;
    private String endDate;
    private String state;
    private String content;

    public Career toCareerEntity(Long id) {
        return Career.builder()
                .tutorId(id)
                .idx(idx)
                .careerType(careerType)
                .careerName(careerName)
                .startDate(startDate)
                .endDate(endDate)
                .state(state)
                .content(content)
                .build();
    }

    public static CareerDto from(Career career) {
        if(career == null) return null;

        return CareerDto.builder()
                .idx(career.getIdx())
                .careerType(career.getCareerType())
                .careerName(career.getCareerName())
                .startDate(career.getStartDate())
                .endDate(career.getEndDate())
                .state(career.getState())
                .content(career.getContent())
                .build();
    }
}
