package com.example.career.domain.community.Dto.response;

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
