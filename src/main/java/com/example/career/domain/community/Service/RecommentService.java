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

    private final RecommentRepository repository;
    private final CommentRepository commentRepository;

    @Transactional
    public Recomment addRecomment(RecommentDto recommentDto, Long userId, String userNickname) {
        commentRepository.incrementRecommentCntByIdAndUserId(recommentDto.getCommentId(), userId);
        return repository.save(recommentDto.toRecommentEntity(userId, userNickname));
    }

    public void updateRecomment(RecommentDto recommentDto, Long userId) {
        repository.updateContentByIdAnduserId(recommentDto.getId(), recommentDto.getContent(), userId, LocalDateTime.now());
    }
    @Transactional
    public void deleteRecommentByUserIdAndId(Long userId, Long id) {
        commentRepository.decrementRecommentCntByIdAndUserId(id, userId);
        repository.deleteByUserIdAndId(userId, id);
    }
}
