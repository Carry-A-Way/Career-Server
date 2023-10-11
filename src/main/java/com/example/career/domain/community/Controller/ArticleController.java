package com.example.career.domain.community.Controller;

import com.example.career.domain.community.Dto.response.ArticleCountByCategoryDto;
import com.example.career.domain.community.Dto.response.ArticleDto;
import com.example.career.domain.community.Entity.Article;
import com.example.career.domain.community.Service.ArticleService;
import com.example.career.global.annotation.Authenticated;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/community/article")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping("all")
    public ResponseEntity<List<ArticleDto>> allArticles(@RequestParam int page, @RequestParam int size) {
        List<ArticleDto> articles = articleService.getAllArticles(page, size);
        return ResponseEntity.ok(articles);
    }

    @Authenticated
    @GetMapping("detail")
    public ResponseEntity<Map<String, Object>> allArticles(@RequestParam Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        Map<String, Object> details = articleService.getArticleInDetail(id, userId);
        return ResponseEntity.ok(details);
    }

    @GetMapping("all_category")
    public ResponseEntity<List<ArticleDto>> allCategoryArticles(@RequestParam int categoryId, @RequestParam int page, @RequestParam int size) {
        List<ArticleDto> articles = articleService.getCategoryArticles(categoryId, page, size);
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/count-by-category")
    public ResponseEntity<List<ArticleCountByCategoryDto>> getArticleCountsByCategory() {
        List<ArticleCountByCategoryDto> counts = articleService.getCountByCategoryId();
        return ResponseEntity.ok(counts);
    }

    @Authenticated
    @PostMapping("/add")
    public ResponseEntity<ArticleDto> addArticle(MultipartHttpServletRequest request) throws Exception {
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

        return ResponseEntity.ok(ArticleDto.from(article));
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