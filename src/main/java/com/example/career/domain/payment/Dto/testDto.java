package com.example.career.domain.payment.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class testDto {
    private Long user_id;
    private String name;
    private String phoneNumber;

}
