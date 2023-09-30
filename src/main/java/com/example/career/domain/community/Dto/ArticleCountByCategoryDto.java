package com.example.career.domain.community.Dto;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleCountByCategoryDto {
    private Long categoryId;
    private Long count;
}
