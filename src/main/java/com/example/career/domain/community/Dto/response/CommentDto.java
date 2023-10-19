package com.example.career.domain.community.Dto.response;

import com.example.career.domain.community.Dto.Brief.ArticleBrief;
import com.example.career.domain.community.Dto.Brief.UserBrief;
import com.example.career.domain.community.Dto.request.CommentDtoReq;
import com.example.career.domain.community.Entity.Article;
import com.example.career.domain.community.Entity.Comment;
import com.example.career.domain.user.Entity.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long id;
    private String content;
    private int heartCnt;
    private int recommentCnt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isHeartClicked;
    private UserBrief user;
    private ArticleBrief article;
    private List<RecommentDto> recomments;
    private Long commentId = -1L;

    public static Comment toCommentEntity(User user, Article article, CommentDtoReq dto) {
        return Comment.builder()
                .user(user)
                .article(article)
                .content(dto.getContent())
                .build();
    }

    public static CommentDto from(Comment comment) {
        if (comment == null) return null;

        CommentDto commentDto = new CommentDto();
        commentDto.id = comment.getId();
        commentDto.content = comment.getContent();
        commentDto.heartCnt = comment.getHeartCnt();
        commentDto.recommentCnt = comment.getRecommentCnt();
        commentDto.createdAt = comment.getCreatedAt();
        commentDto.updatedAt = comment.getUpdatedAt();
        commentDto.user = new UserBrief(comment.getUser());
        commentDto.article = new ArticleBrief(comment.getArticle());


        // Mapping recomments
        List<RecommentDto> recommentDtos = comment.getRecomments().stream()
                .map(RecommentDto::from)
                .collect(Collectors.toList());
        commentDto.setRecomments(recommentDtos);

        return commentDto;
    }

    public static CommentDto fromSqlResult(SqlResultCommentDto dto) {
        CommentDto commentDto = new CommentDto();

        commentDto.id = dto.getId();
        commentDto.heartCnt = dto.getHeartCnt();
        commentDto.recommentCnt = dto.getRecommentCnt();
        commentDto.content = dto.getContent();
        commentDto.createdAt = dto.getCreatedAt();
        commentDto.user = new UserBrief(dto.getUserId(), dto.getUserNickname(),
                dto.getUserIsTutor(), dto.getUserProfileImg());
        commentDto.article = new ArticleBrief(dto.getArticleId(), dto.getArticleTitle());
        commentDto.commentId = dto.getCommentId();
        return commentDto;
    }

}