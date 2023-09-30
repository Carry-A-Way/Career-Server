package com.example.career.domain.community.Controller;

import com.example.career.domain.community.Dto.ArticleDto;
import com.example.career.domain.community.Entity.Article;
import com.example.career.domain.community.Repository.ArticleRepository;
import com.example.career.domain.community.Service.ArticleService;
import com.example.career.domain.user.Dto.SignUpReqDto;
import com.example.career.global.annotation.Authenticated;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/community/article")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping("all")
    public ResponseEntity<List<Article>> allArticles(@RequestParam int page, @RequestParam int size) {
        List<Article> articles = articleService.getAllArticles(page, size);
        return ResponseEntity.ok(articles);
    }

    @GetMapping("all_category")
    public ResponseEntity<List<Article>> allCategoryArticles(@RequestParam int categoryId, @RequestParam int page, @RequestParam int size) {
        List<Article> articles = articleService.getCategoryArticles(categoryId, page, size);
        return ResponseEntity.ok(articles);
    }

    @Authenticated
    @PostMapping("/add")
    public ResponseEntity<Article> addArticle(MultipartHttpServletRequest request) throws Exception {
        Long userId = (Long) request.getAttribute("userId");

        // 파일 데이터 추출
        List<MultipartFile> multipartFiles = request.getFiles("images");

        // JSON 데이터 추출
        String jsonStr = request.getParameter("json");
        ArticleDto articleDto = new ObjectMapper().readValue(jsonStr, ArticleDto.class);

        List<String> imgUrls = new ArrayList<>();
        if (multipartFiles != null && !multipartFiles.isEmpty()) {
            imgUrls = articleService.uploadImages(multipartFiles);
        }

        articleDto.setImgUrls(imgUrls);

        Article article = articleService.addArticle(articleDto, userId);

        return ResponseEntity.ok(article);
    }



    @PostMapping("/modify")
    public ResponseEntity<Object> modifyArticle(MultipartHttpServletRequest request) throws Exception {
        Long userId = (Long) request.getAttribute("userId");

        // 파일 데이터 추출
        List<MultipartFile> multipartFiles = request.getFiles("images");

        // JSON 데이터 추출
        String jsonStr = request.getParameter("json");
        ArticleDto articleDto = new ObjectMapper().readValue(jsonStr, ArticleDto.class);

        List<String> removedImgUrls = articleDto.getRemovedImageUrls();


        // 새 이미지들 처리
        List<String> newImgUrls = articleService.uploadImages(multipartFiles);

        articleService.updateArticle(articleDto, newImgUrls, userId);

        return ResponseEntity.ok().build();
    }

    @Authenticated
    @DeleteMapping("delete")
    public ResponseEntity<Object> deleteArticle(@RequestBody ArticleDto articleDto, HttpServletRequest request) throws Exception {
        Long userId = (Long) request.getAttribute("userId");
        articleService.deleteArticleByUserIdAndId(articleDto.getId(), userId);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> ExceptionHandler(Exception e) {
        log.info("Exception msg = {}", e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}