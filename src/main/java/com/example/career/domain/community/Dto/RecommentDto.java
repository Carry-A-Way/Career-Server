package com.example.career.domain.community.Dto;

import com.example.career.domain.community.Entity.Comment;
import com.example.career.domain.community.Entity.Recomment;
import com.example.career.domain.community.Repository.CommentRepository;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

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

    public Recomment toRecommentEntity(Long userId, String userNickname, Boolean isTutor, Comment comment) {

        return Recomment.builder()
                .userId(userId)
                .userNickname(userNickname)
                .isTutor(isTutor)
                .articleId(articleId)
                .comment(comment)
                .content(content)
                .heartCnt(heartCnt)
                .build();
    }
}
