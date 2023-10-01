package com.example.career.domain.community.Controller;

import com.example.career.domain.community.Dto.ArticleDto;
import com.example.career.domain.community.Dto.HeartDto;
import com.example.career.domain.community.Entity.Article;
import com.example.career.domain.community.Entity.Heart;
import com.example.career.domain.community.Repository.ArticleRepository;
import com.example.career.domain.community.Repository.HeartRepository;
import com.example.career.domain.community.Service.ArticleService;
import com.example.career.domain.community.Service.CommentService;
import com.example.career.domain.community.Service.HeartService;
import com.example.career.domain.community.Service.RecommentService;
import com.example.career.global.annotation.Authenticated;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/community/heart")
@RequiredArgsConstructor
public class HeartController {

    private final HeartService heartService;

    @Authenticated
    @GetMapping("my_hearts")
    public ResponseEntity<List<Article>> allThumbsUpArticles(@RequestParam int page, @RequestParam int size, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        List<Article> articles = heartService.getAllThumbsUpArticles(userId, page, size);
        return ResponseEntity.ok(articles);
    }

    @Authenticated
    @PostMapping("/add")
    public ResponseEntity<Heart> addHeart(@RequestBody HeartDto heartDto, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        Heart heart = heartService.addHeart(heartDto, userId);
        return ResponseEntity.ok(heart);
    }

    @Authenticated
    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteArticle(@RequestBody HeartDto heartDto, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        heartService.deleteHeart(heartDto, userId);
        return ResponseEntity.ok().build();
    }

}