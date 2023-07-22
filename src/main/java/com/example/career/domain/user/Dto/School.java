package com.example.career.domain.user.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class School {
    private Long id; // 경력 갯수 id
    private String school; // 고등, 대학
    private String schoolName;
    private String startDate;
    private String endDate;
    private String state; // 졸업, 졸업예정 등

}
