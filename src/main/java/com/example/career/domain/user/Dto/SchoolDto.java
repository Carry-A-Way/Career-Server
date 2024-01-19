package com.example.career.domain.user.Dto;

import com.example.career.domain.user.Entity.School;
import com.example.career.domain.user.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SchoolDto {
    private Long idx; // 경력 갯수 idx
    private String schoolType; // 고등, 대학
    private String schoolName;
    private String majorName;
    private String startDate;
    private String endDate;
    private String state; // 졸업, 졸업예정 등

    public School toSchoolEntity(Long id) {
        return School.builder()
                .tutorId(id)
                .idx(idx)
                .schoolName(schoolName)
                .schoolType(schoolType)
                .majorName(majorName)
                .startDate(startDate)
                .endDate(endDate)
                .state(state)
                .build();
    }

    public static SchoolDto from(School school) {
        if(school == null) return null;

        return SchoolDto.builder()
                .idx(school.getIdx())
                .schoolType(school.getSchoolType())
                .schoolName(school.getSchoolName())
                .majorName(school.getMajorName())
                .startDate(school.getStartDate())
                .endDate(school.getEndDate())
                .state(school.getState())
                .build();
    }
}
