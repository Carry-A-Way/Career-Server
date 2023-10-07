package com.example.career.domain.community.Service;

import com.example.career.domain.community.Dto.ArticleCountByCategoryDto;
import com.example.career.domain.community.Dto.ArticleDto;
import com.example.career.domain.community.Entity.Article;
import com.example.career.domain.community.Entity.Comment;
import com.example.career.domain.community.Entity.Recomment;
import com.example.career.domain.community.Repository.ArticleRepository;

import com.example.career.domain.community.Repository.CommentRepository;
import com.example.career.domain.community.Repository.RecommentRepository;

import com.example.career.global.utils.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final RecommentRepository recommentRepository;

    private final S3Uploader s3Uploader;

    public List<Article> getAllArticles(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Article> result = articleRepository.findAll(pageable);
        return result.getContent();
    }

    public List<Article> getCategoryArticles(int categoryId, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Article> result = articleRepository.findByCategoryId(categoryId, pageable);
        return result.getContent();
    }

    public Article addArticle(ArticleDto articleDto, Long userId, String userNickname) {
        Article article = articleRepository.save(articleDto.toArticleEntity(userId, userNickname));
        return article;
    }

    public List<String> uploadImages(List<MultipartFile> MultipartFile) throws IOException {
        List<String> urlList = s3Uploader.upload(MultipartFile,"static/article");
        return urlList;
    }

    private void deleteUploadedImage(String url) {
        s3Uploader.deleteFile(url);
    }

    public void updateArticle(ArticleDto articleDto, List<String> newImgUrls, Long userId) throws Exception {
//        articleRepository.updateArticleTitleAndContent(userId, articleDto.getId(), articleDto.getTitle(), articleDto.getContent(), LocalDateTime.now());
        // 기존 Article 조회
        Article article = articleRepository.findById(articleDto.getId())
                .orElseThrow(() -> new Exception("Article not found"));

        // Article 정보 및 이미지 URL 업데이트
        article.setTitle(articleDto.getTitle());
        article.setContent(articleDto.getContent());
        article.setCategoryId(articleDto.getCategoryId());

        // 이미지 URL 업데이트
        List<String> currentImages = Arrays.asList(article.getImg1(), article.getImg2(), article.getImg3(), article.getImg4(), article.getImg5(), article.getImg6());
        // 이미지 업로드 한 것 삭제
        for (String url: articleDto.getRemovedImageUrls()) {
            if (currentImages.contains(url)) {
                deleteUploadedImage(url);
            }
        }

        currentImages = currentImages.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        currentImages.removeAll(articleDto.getRemovedImageUrls());

        currentImages.addAll(newImgUrls);
        article.setImg1(currentImages.size() > 0 ? currentImages.get(0) : null);
        article.setImg2(currentImages.size() > 1 ? currentImages.get(1) : null);
        article.setImg3(currentImages.size() > 2 ? currentImages.get(2) : null);
        article.setImg4(currentImages.size() > 3 ? currentImages.get(3) : null);
        article.setImg5(currentImages.size() > 4 ? currentImages.get(4) : null);
        article.setImg6(currentImages.size() > 5 ? currentImages.get(5) : null);

        article.setUpdatedAt(LocalDateTime.now());

        articleRepository.save(article);
    }

    @Transactional
    public void deleteArticleByUserIdAndId(Long id, Long userId) throws Exception {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new Exception("Article not found"));
        List<String> images = Arrays.asList(article.getImg1(), article.getImg2(), article.getImg3(), article.getImg4(), article.getImg5(), article.getImg6());
        images = images.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        for (String url: images) {
            s3Uploader.deleteFile(url);
        }
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

    public List<ArticleCountByCategoryDto> getCountByCategoryId() {
            List<ArticleCountByCategoryDto> articleCountByCategoryDtos = articleRepository.countArticlesByCategoryId();
            return articleCountByCategoryDtos;
    }
}