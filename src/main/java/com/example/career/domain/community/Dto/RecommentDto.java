package com.example.career.domain.community.Dto;

import com.example.career.domain.community.Dto.request.RecommentDtoReq;
import com.example.career.domain.community.Entity.Article;
import com.example.career.domain.community.Entity.Comment;
import com.example.career.domain.community.Entity.Recomment;
import com.example.career.domain.community.Repository.CommentRepository;
import com.example.career.domain.user.Entity.User;
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
    private int heartCnt;

    public static Recomment toRecommentEntity(User user, Article article, Comment comment, RecommentDtoReq dto) {

        return Recomment.builder()
                .user(user)
                .article(article)
                .comment(comment)
                .content(dto.getContent())
                .build();
    }
}
