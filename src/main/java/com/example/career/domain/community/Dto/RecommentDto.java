package com.example.career.domain.community.Dto;

import com.example.career.domain.community.Entity.Comment;
import com.example.career.domain.community.Entity.Recomment;
import lombok.*;

@Data
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RecommentDto {
    private Long id;
    private Long userId;
    private Long articleId;
    private Long commentId;
    private String content;
    private int heartCnt;

    public Recomment toRecommentEntity(Long userId, String userNickname) {
        return Recomment.builder()
                .userId(userId)
                .userNickname(userNickname)
                .articleId(articleId)
                .commentId(commentId)
                .content(content)
                .heartCnt(heartCnt)
                .build();

    }
}
