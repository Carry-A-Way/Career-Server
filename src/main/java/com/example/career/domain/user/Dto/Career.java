package com.example.career.domain.user.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Career {
    private Long id;
    private String career;
    private String careerName;
    private String startDate;
    private String endDate;
    private String state;
    private String content;
}
