package com.example.career.domain.community.Dto.request;

import lombok.*;

@Data
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RecommentDtoReq {
    private Long id;
    private Long articleId;
    private Long commentId;
    private String content;
}
