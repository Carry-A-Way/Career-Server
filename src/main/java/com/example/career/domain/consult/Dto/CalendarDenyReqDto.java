package com.example.career.domain.consult.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalendarDenyReqDto {
    private Long id;
    private Long consultId;
    private String reason;
}
