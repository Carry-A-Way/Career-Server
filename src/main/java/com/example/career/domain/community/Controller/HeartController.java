package com.example.career.domain.community.Controller;

import com.example.career.domain.community.Dto.HeartDto;
import com.example.career.domain.community.Dto.response.ArticleDto;
import com.example.career.domain.community.Entity.Article;
import com.example.career.domain.community.Entity.Heart;
import com.example.career.domain.community.Service.HeartService;
import com.example.career.domain.user.Entity.User;
import com.example.career.global.annotation.Authenticated;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<ArticleDto>> allThumbsUpArticles(@RequestParam int page, @RequestParam int size, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        Long userId = user.getId();
        List<ArticleDto> articleDtos = heartService.getAllThumbsUpArticles(userId, page, size);
        return ResponseEntity.ok(articleDtos);
    }

    @Authenticated
    @PostMapping("/add")
    public ResponseEntity<Heart> addHeart(@RequestBody HeartDto heartDto, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        Long userId = user.getId();
        Heart heart = heartService.addHeart(heartDto, userId);
        return ResponseEntity.ok(heart);
    }

    @Authenticated
    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteArticle(@RequestBody HeartDto heartDto, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        Long userId = user.getId();
        heartService.deleteHeart(heartDto, userId);
        return ResponseEntity.ok().build();
    }

}