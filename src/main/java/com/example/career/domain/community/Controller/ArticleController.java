package com.example.career.domain.community.Controller;

import com.example.career.domain.community.Dto.ArticleDto;
import com.example.career.domain.community.Entity.Article;
import com.example.career.domain.community.Repository.ArticleRepository;
import com.example.career.domain.community.Service.ArticleService;
import com.example.career.global.annotation.Authenticated;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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

        @GetMapping("detail")
    public ResponseEntity<Map<String, Object>> allArticles(@RequestParam Long id) {
        Map<String, Object> details = articleService.getArticleInDetail(id);
        return ResponseEntity.ok(details);
    }

    @Authenticated
    @PostMapping("/add")
    public ResponseEntity<Article> addArticle(@RequestBody ArticleDto articleDto, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        Article article = articleService.addArticle(articleDto, userId);
        return ResponseEntity.ok(article);
    }

    @PostMapping("/modify")
    public ResponseEntity<Object> modifyArticle(@RequestBody ArticleDto articleDto, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        articleService.updateArticle(articleDto, userId);
        return ResponseEntity.ok().build();
    }

    @Authenticated
    @DeleteMapping("delete")
    public ResponseEntity<Object> deleteArticle(@RequestBody ArticleDto articleDto, HttpServletRequest request) {
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