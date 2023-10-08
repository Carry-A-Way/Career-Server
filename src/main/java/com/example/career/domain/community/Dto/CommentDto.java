package com.example.career.domain.community.Dto;

import com.example.career.domain.community.Entity.Article;
import com.example.career.domain.community.Entity.Comment;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long id;
    private Long userId;
    private String userNickname;
    private Boolean isTutor;
    private Long articleId;
    private String content;
    private int heartCnt;
    private int recommentCnt;
    private String articleTitle;
    private LocalDateTime createdAt;

    public Comment toCommentEntity(Long userId, String userNickname, Boolean isTutor) {
        return Comment.builder()
                .userId(userId)
                .userNickname(userNickname)
                .isTutor(isTutor)
                .articleId(articleId)
                .content(content)
                .heartCnt(heartCnt)
                .recommentCnt(recommentCnt)
                .build();

    }
}
