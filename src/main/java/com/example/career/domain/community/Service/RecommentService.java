package com.example.career.domain.community.Service;

import com.example.career.domain.community.Dto.CommentDto;
import com.example.career.domain.community.Dto.RecommentDto;
import com.example.career.domain.community.Entity.Comment;
import com.example.career.domain.community.Entity.Recomment;
import com.example.career.domain.community.Repository.CommentRepository;
import com.example.career.domain.community.Repository.RecommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RecommentService {

    private final RecommentRepository recommentRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public Recomment addRecomment(RecommentDto recommentDto, Long userId, String userNickname, Boolean isTutor) {
        // 댓글 엔터티를 조회
        Comment commentEntity = commentRepository.findById(recommentDto.getCommentId())
                .orElseThrow(() -> new IllegalArgumentException("Comment not found with ID: " + recommentDto.getCommentId()));

        commentRepository.incrementRecommentCntByIdAndUserId(recommentDto.getCommentId(), userId);
        return recommentRepository.save(recommentDto.toRecommentEntity(userId, userNickname, isTutor, commentEntity));
    }

    public void updateRecomment(RecommentDto recommentDto, Long userId) {
        recommentRepository.updateContentByIdAnduserId(recommentDto.getId(), recommentDto.getContent(), userId, LocalDateTime.now());
    }
    @Transactional
    public void deleteRecommentByUserIdAndId(Long userId, Long id) {
        commentRepository.decrementRecommentCntByIdAndUserId(id, userId);
        recommentRepository.deleteByUserIdAndId(userId, id);
    }
}
