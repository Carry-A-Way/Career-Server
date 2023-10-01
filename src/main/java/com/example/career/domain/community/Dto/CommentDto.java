package com.example.career.domain.community.Dto;

import com.example.career.domain.community.Entity.Article;
import com.example.career.domain.community.Entity.Comment;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long id;
    private Long userId;
    private Long articleId;
    private String content;
    private int heartCnt;
    private int recommentCnt;

    private String articleTitle;

    public Comment toCommentEntity(Long userId) {
        return Comment.builder()
                .userId(userId)
                .articleId(articleId)
                .content(content)
                .heartCnt(heartCnt)
                .recommentCnt(recommentCnt)
                .build();

    }
}
