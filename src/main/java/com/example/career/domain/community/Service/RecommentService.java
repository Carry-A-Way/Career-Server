package com.example.career.domain.community.Service;

import com.example.career.domain.community.Dto.response.RecommentDto;
import com.example.career.domain.community.Dto.request.RecommentDtoReq;
import com.example.career.domain.community.Entity.Article;
import com.example.career.domain.community.Entity.Comment;
import com.example.career.domain.community.Entity.Recomment;
import com.example.career.domain.community.Repository.ArticleRepository;
import com.example.career.domain.community.Repository.CommentRepository;
import com.example.career.domain.community.Repository.RecommentRepository;
import com.example.career.domain.user.Entity.User;
import com.example.career.domain.user.Repository.UserRepository;
import com.example.career.global.time.KoreaTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RecommentService {

    private final RecommentRepository recommentRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

    @Transactional
    public Recomment addRecomment(RecommentDtoReq recommentDtoReq, Long userId) {
        // 유저 엔터티를 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        // 게시글 엔터티를 조회
        Article article = articleRepository.findById(recommentDtoReq.getArticleId())
                .orElseThrow(() -> new IllegalArgumentException("Comment not found with ID: " + recommentDtoReq.getArticleId()));

        // 댓글 엔터티를 조회
        Comment commentEntity = commentRepository.findById(recommentDtoReq.getCommentId())
                .orElseThrow(() -> new IllegalArgumentException("Comment not found with ID: " + recommentDtoReq.getCommentId()));

        articleRepository.incrementArticleCommentCnt(article.getId());
        commentRepository.incrementRecommentCntByIdAndUserId(recommentDtoReq.getCommentId());
        return recommentRepository.save(RecommentDto.toRecommentEntity(user, article, commentEntity, recommentDtoReq));
    }

    public void updateRecomment(RecommentDtoReq recommentDtoReq, Long userId) {
        recommentRepository.updateContentByIdAnduserId(recommentDtoReq.getId(), recommentDtoReq.getContent(), userId, KoreaTime.now());
    }
    @Transactional
    public void deleteRecommentByUserIdAndId(Long userId, RecommentDtoReq recommentDtoReq) {
        articleRepository.decrementArticleCommentCnt(recommentDtoReq.getArticleId());
        commentRepository.decrementRecommentCntByIdAndUserId(recommentDtoReq.getCommentId());
        recommentRepository.deleteByUserIdAndId(userId, recommentDtoReq.getId());
    }
}
