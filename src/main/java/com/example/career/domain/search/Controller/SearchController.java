package com.example.career.domain.search.Controller;

import com.example.career.domain.community.Dto.response.ArticleDto;
import com.example.career.domain.community.Entity.Article;
import com.example.career.domain.community.Repository.ArticleRepository;
import com.example.career.domain.community.Repository.CommentRepository;
import com.example.career.domain.search.Dto.CommunitySearchRespDto;
import com.example.career.domain.search.Service.SearchService;
import com.example.career.domain.user.Entity.User;
import com.example.career.global.annotation.Authenticated;
import com.example.career.global.valid.ValidCheck;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("search")
public class SearchController {
    private final SearchService searchService;
    @Authenticated
    @GetMapping("community")
    public ResponseEntity<List<ArticleDto>> searchCommunityList(@RequestParam String keyWord, @RequestParam int page, @RequestParam int size, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        Long userId = user.getId();
        return ResponseEntity.ok(searchService.getArticlesByKeyWord(userId, keyWord, page, size));
    }
}
