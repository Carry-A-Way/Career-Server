package com.example.career.domain.community.Service;

import com.example.career.domain.community.Dto.HeartDto;
import com.example.career.domain.community.Entity.Article;
import com.example.career.domain.community.Entity.Heart;
import com.example.career.domain.community.Repository.ArticleRepository;
import com.example.career.domain.community.Repository.CommentRepository;
import com.example.career.domain.community.Repository.HeartRepository;
import com.example.career.domain.community.Repository.RecommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HeartService {

    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final RecommentRepository recommentRepository;
    private final HeartRepository heartRepository;

    public List<Article> getAllThumbsUpArticles(Long userId, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Article> result = heartRepository.findArticlesByUserIdAndType(userId, 0, pageable);
        return result.getContent();
    }

    @Transactional
    public Heart addHeart(HeartDto heartDto, Long userId) {
        int type = heartDto.getType();
        if (type == 0) {
            articleRepository.incrementArticleThumbsUp(heartDto.getTypeId(), userId);
        } else if (heartDto.getType() == 1) { // 댓글에 좋아요
            commentRepository.incrementThumbsUpCnt(heartDto.getTypeId(), userId);
        } else { // 대댓글에 좋아요
            recommentRepository.incrementThumbsUpCnt(heartDto.getTypeId(), userId);
        }
        return heartRepository.save(heartDto.toHeartEntity(userId));
    }

    @Transactional
    public void deleteHeart(HeartDto heartDto, Long userId) {
        int type = heartDto.getType();
        if (type == 0) {
            articleRepository.decrementArticleThumbsUp(heartDto.getTypeId(), userId);
        } else if (heartDto.getType() == 1) { // 댓글에 좋아요
            commentRepository.decrementThumbsUpCnt(heartDto.getTypeId(), userId);
        } else { // 대댓글에 좋아요
            recommentRepository.decrementThumbsUpCnt(heartDto.getTypeId(), userId);
        }
        heartRepository.deleteByUserIdAndTypeIdAndType(userId, heartDto.getTypeId(), heartDto.getType());
    }
}