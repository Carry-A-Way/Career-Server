package com.example.career.domain.user.Dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ListDeleteReqDto {
    private List<SchoolDto> schoolList;

    private List<CareerDto> careerList;

    private List<TagDto> tagList;

}
