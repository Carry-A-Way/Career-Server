package com.example.career.domain.major.Dto;

import com.example.career.domain.major.Entity.Major;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MajorReqDto {
    List<Major> majors;
}
