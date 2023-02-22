package com.example.career.Dto;

import com.example.career.Entity.TestEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class testDto {
    private Long user_id;
    private String name;
    private String phoneNumber;

}
