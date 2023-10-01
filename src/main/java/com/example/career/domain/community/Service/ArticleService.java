package com.example.career.domain.community.Service;

import com.example.career.domain.community.Dto.ArticleDto;
import com.example.career.domain.community.Entity.Article;
import com.example.career.domain.community.Entity.Comment;
import com.example.career.domain.community.Entity.Recomment;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final RecommentRepository recommentRepository;

    public List<Article> getAllArticles(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Article> result = articleRepository.findAll(pageable);
        return result.getContent();
    }

    public Article addArticle(ArticleDto articleDto, Long userId) {
        Article article = articleRepository.save(articleDto.toArticleEntity(userId));
        return article;
    }

    @Transactional
    public void updateArticle(ArticleDto articleDto, Long userId) {
        articleRepository.updateArticleTitleAndContent(userId, articleDto.getId(), articleDto.getTitle(), articleDto.getContent(), LocalDateTime.now());
    }

    @Transactional
    public void deleteArticleByUserIdAndId(Long id, Long userId) {
        articleRepository.deleteByIdAndUserId(id, userId);
    }

    public Map<String, Object> getArticleInDetail(Long id) {
        Map<String, Object> details = new HashMap<>();

        // 게시글 가져오기
        Article article = articleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Article not found"));
        details.put("article", article);

        // 해당 게시글의 댓글 가져오기
        List<Comment> comments = commentRepository.findByArticleId(id);
        details.put("comments", comments);

        // 해당 게시글의 대댓글 가져오기
        List<Recomment> recomments = recommentRepository.findByArticleId(id);
        details.put("recomments", recomments);

        return details;
    }
}