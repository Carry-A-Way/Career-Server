package com.example.career.domain.community.Service;

import com.example.career.domain.community.Dto.response.CommentDto;
import com.example.career.domain.community.Dto.response.RecommentDto;
import com.example.career.domain.community.Dto.response.SqlResultCommentDto;
import com.example.career.domain.community.Dto.request.CommentDtoReq;
import com.example.career.domain.community.Entity.Article;
import com.example.career.domain.community.Entity.Comment;
import com.example.career.domain.community.Entity.Heart;
import com.example.career.domain.community.Repository.ArticleRepository;
import com.example.career.domain.community.Repository.CommentRepository;
import com.example.career.domain.community.Repository.HeartRepository;
import com.example.career.domain.user.Entity.User;
import com.example.career.domain.user.Repository.UserRepository;
import com.example.career.global.time.KoreaTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final HeartRepository heartRepository;

    public List<CommentDto> CheckIsHeartClicked(List<CommentDto> commentDtos, Long userId) {
        List<Heart> likedComments = heartRepository.findByUserIdAndType(userId, 1);
        List<Heart> likedRecomments = heartRepository.findByUserIdAndType(userId, 2);

        return commentDtos.stream()
                .map(commentDto -> {
                    if (commentDto.getCommentId() != -1) { // comment에 해당
                        commentDto.setIsHeartClicked(likedComments.stream().anyMatch(heart -> heart.getTypeId().equals(commentDto.getId())));
                    } else {
                        commentDto.setIsHeartClicked(likedRecomments.stream().anyMatch(heart -> heart.getTypeId().equals(commentDto.getId())));
                    }
                    return commentDto;
                })
                .collect(Collectors.toList());
    }

    public List<CommentDto> convertToCommentDtoListWithRecommentDtosInside(List<Comment> comments, Long userId) {
        List<Heart> likedComments = heartRepository.findByUserIdAndType(userId, 1);
        List<Heart> likedRecomments = heartRepository.findByUserIdAndType(userId, 2);
        List<CommentDto> commentDtos = comments.stream().map(comment -> {
            CommentDto commentDto = CommentDto.from(comment);
            // 댓글 isLiked 설정
            commentDto.setIsHeartClicked(likedComments.stream().anyMatch(heart -> heart.getTypeId().equals(comment.getId())));
            for (RecommentDto recommentDto : commentDto.getRecomments()) {
                // 대댓글 isLiked 설정
                recommentDto.setIsHeartClicked(likedRecomments.stream().anyMatch(heart -> heart.getTypeId().equals(recommentDto.getId())));
            }
            return commentDto;
        }).collect(Collectors.toList());
        return commentDtos;
    }

    private List<CommentDto> convertToCommentDtoFromSqlResultDto(List<SqlResultCommentDto> sqlResults) {
        return sqlResults.stream()
                .map(CommentDto::fromSqlResult)
                .collect(Collectors.toList());
    }

    public List<CommentDto> allComments(Long userId, int page, int size) {
        List<SqlResultCommentDto> sqlResults = commentRepository.findCombinedCommentsByUserId(userId, page * size, size);
        List<CommentDto> commentDtos = convertToCommentDtoFromSqlResultDto(sqlResults);
        return CheckIsHeartClicked(commentDtos, userId);
    }

    @Transactional
    public Comment addComment(CommentDtoReq commentDtoReq, Long userId) {
        // 유저 엔터티를 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
        // 게시글 엔티티 조회
        Article article = articleRepository.findById(commentDtoReq.getArticleId())
                .orElseThrow(() -> new IllegalArgumentException("Article not found with ID: " + commentDtoReq.getArticleId()));

        articleRepository.incrementArticleCommentCnt(article.getId());
        return commentRepository.save(CommentDto.toCommentEntity(user, article, commentDtoReq));
    }

    public void updateComment(CommentDtoReq commentDtoReq, Long userId) {
        commentRepository.updateContentByuserIdAndId(commentDtoReq.getId(), commentDtoReq.getContent(), userId, KoreaTime.now());
    }

    @Transactional
    public void deleteCommentByUserIdAndId(Long userId, CommentDtoReq commentDtoReq) {
        //대댓글 유무 확인
        Comment comment = commentRepository.findById(commentDtoReq.getId())
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        boolean hasRecomments = !comment.getRecomments().isEmpty();
        articleRepository.decrementArticleCommentCnt(comment.getArticle().getId());

        //대댓글이 없는 경우
        if (!hasRecomments) {
            commentRepository.deleteByUserIdAndId(userId, commentDtoReq.getId());
        }
        else { //대댓글이 있는 경우
            comment.setIsDeleted(true);
            commentRepository.save(comment);
        }
    }
}
