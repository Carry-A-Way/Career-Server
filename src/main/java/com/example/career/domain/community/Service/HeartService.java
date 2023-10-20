package com.example.career.domain.community.Service;

import com.example.career.domain.community.Dto.HeartDto;
import com.example.career.domain.community.Dto.response.ArticleDto;
import com.example.career.domain.community.Entity.Article;
import com.example.career.domain.community.Entity.Heart;
import com.example.career.domain.community.Repository.ArticleRepository;
import com.example.career.domain.community.Repository.CommentRepository;
import com.example.career.domain.community.Repository.HeartRepository;
import com.example.career.domain.community.Repository.RecommentRepository;
import com.example.career.domain.user.Entity.User;
import com.example.career.domain.user.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HeartService {
    private final ArticleService articleService;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final RecommentRepository recommentRepository;
    private final HeartRepository heartRepository;
    private final UserRepository userRepository;

    public List<ArticleDto> getAllThumbsUpArticles(Long userId, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Article> result = heartRepository.findArticlesByUserIdAndType(userId, 0, pageable);
        List<Article> articles = result.getContent();

        return articleService.convertToArticleDtoList(articles, userId);
    }

    @Transactional
    public Heart addHeart(HeartDto heartDto, Long userId) {
        // 유저 엔터티를 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
        // 게시글 엔티티 조회
        int type = heartDto.getType();
        if (type == 0) {
            articleRepository.incrementArticleThumbsUp(heartDto.getTypeId());
        } else if (heartDto.getType() == 1) { // 댓글에 좋아요
            commentRepository.incrementThumbsUpCnt(heartDto.getTypeId());
        } else { // 대댓글에 좋아요
            recommentRepository.incrementThumbsUpCnt(heartDto.getTypeId());
        }
        return heartRepository.save(heartDto.toHeartEntity(user));
    }

    @Transactional
    public void deleteHeart(HeartDto heartDto, Long userId) {
        int type = heartDto.getType();
        if (type == 0) {
            articleRepository.decrementArticleThumbsUp(heartDto.getTypeId());
        } else if (heartDto.getType() == 1) { // 댓글에 좋아요
            commentRepository.decrementThumbsUpCnt(heartDto.getTypeId());
        } else { // 대댓글에 좋아요
            recommentRepository.decrementThumbsUpCnt(heartDto.getTypeId());
        }
        heartRepository.deleteByUserIdAndTypeIdAndType(userId, heartDto.getTypeId(), heartDto.getType());
    }
}