package com.example.career.domain.community.Service;

import com.example.career.domain.community.Dto.CommentDto;
import com.example.career.domain.community.Entity.Article;
import com.example.career.domain.community.Entity.Comment;
import com.example.career.domain.community.Repository.ArticleRepository;
import com.example.career.domain.community.Repository.CommentRepository;
import com.example.career.domain.community.Repository.RecommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    public List<CommentDto> allComments(Long userId, int page, int size) {
        return commentRepository.findCombinedCommentsByUserId(userId, page*size, size);
    }

    @Transactional
    public Comment addComment(CommentDto commentDto, Long userId, String userNickname) {
        articleRepository.incrementArticleCommentCnt(commentDto.getArticleId(), userId);
        return commentRepository.save(commentDto.toCommentEntity(userId, userNickname));
    }

    public void updateComment(CommentDto commentDto, Long userId) {
        commentRepository.updateContentByuserIdAndId(commentDto.getId(), commentDto.getContent(), userId, LocalDateTime.now());
    }

    @Transactional
    public void deleteCommentByUserIdAndId(Long userId, Long id) {
        articleRepository.decrementArticleCommentCnt(id, userId);
        commentRepository.deleteByUserIdAndId(userId, id);
    }
}
