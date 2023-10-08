package com.example.career.domain.community.Dto.request;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDtoReq {
    private Long id;
    private Long articleId;
    private String content;
}
