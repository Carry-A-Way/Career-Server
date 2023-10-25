package com.example.career.domain.community.Dto.response;

import com.example.career.domain.community.Dto.Brief.ArticleBrief;
import com.example.career.domain.community.Dto.Brief.CommentBrief;
import com.example.career.domain.community.Dto.Brief.UserBrief;
import com.example.career.domain.community.Dto.request.RecommentDtoReq;
import com.example.career.domain.community.Entity.Article;
import com.example.career.domain.community.Entity.Comment;
import com.example.career.domain.community.Entity.Recomment;
import com.example.career.domain.community.Repository.CommentRepository;
import com.example.career.domain.user.Entity.User;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecommentDto {
    private Long id;
    private String content;
    private int heartCnt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isHeartClicked;
    private UserBrief user;
    private CommentBrief comment;

    public static Recomment toRecommentEntity(User user, Article article, Comment comment, RecommentDtoReq dto) {

        return Recomment.builder()
                .user(user)
                .article(article)
                .comment(comment)
                .content(dto.getContent())
                .build();
    }

    public static RecommentDto from(Recomment recomment) {
        if (recomment == null) return null;

        RecommentDto recommentDto = new RecommentDto();
        recommentDto.id = recomment.getId();
        recommentDto.content = recomment.getContent();
        recommentDto.heartCnt = recomment.getHeartCnt();
        recommentDto.createdAt = recomment.getCreatedAt();
        recommentDto.updatedAt = recomment.getUpdatedAt();
        recommentDto.user = new UserBrief(recomment.getUser());
        recommentDto.comment = new CommentBrief(recomment.getComment());

        return recommentDto;
    }

}
