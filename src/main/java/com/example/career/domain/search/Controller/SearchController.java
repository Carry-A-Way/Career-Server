package com.example.career.domain.search.Controller;

import com.example.career.domain.community.Dto.Brief.UserBrief;
import com.example.career.domain.community.Dto.Brief.UserBriefWithRate;
import com.example.career.domain.community.Dto.response.ArticleDto;
import com.example.career.domain.community.Entity.Article;
import com.example.career.domain.community.Repository.ArticleRepository;
import com.example.career.domain.community.Repository.CommentRepository;
import com.example.career.domain.major.Entity.Major;
import com.example.career.domain.major.Service.MajorService;
import com.example.career.domain.search.Dto.CommunitySearchRespDto;
import com.example.career.domain.search.Service.SearchService;
import com.example.career.domain.user.Entity.TutorDetail;
import com.example.career.domain.user.Entity.User;
import com.example.career.global.annotation.Authenticated;
import com.example.career.global.valid.ValidCheck;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("search")
public class SearchController {
    private final SearchService searchService;
    private final MajorService majorService;
    @Authenticated
    @GetMapping("community")
    public ResponseEntity<List<ArticleDto>> searchCommunityList(@RequestParam String keyWord, @RequestParam int page, @RequestParam int size, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        Long userId = user.getId();
        return ResponseEntity.ok(searchService.getArticlesByKeyWord(userId, keyWord, page, size));
    }
    @Authenticated
    @GetMapping("mentor")
    public ResponseEntity<List<UserBriefWithRate>> searchMentorByTags(HttpServletRequest request, @RequestParam(required = false) String keyWord, @RequestParam String type, @RequestParam int page, @RequestParam int size) {
        User user = (User) request.getAttribute("user");
        if(type.equals("recently")) {
            return ResponseEntity.ok(searchService.getUserByRecently(page, size, user.getId()));

        }else {
            return ResponseEntity.ok(searchService.getUserByTags(keyWord, type, page, size, user.getId()));

        }
    }
    @Authenticated
    @GetMapping("mentor/heart")
    public ResponseEntity<?> getHeartMentorListOfMentee(HttpServletRequest request, @RequestParam int page, @RequestParam int size) {
        User user = (User) request.getAttribute("user");
        List<UserBriefWithRate> userBriefWithRates = searchService.MyClickedHeartMentor(page, size, user.getId());
        if(userBriefWithRates.size() == 0) {
            return ResponseEntity.ok("좋아요 누른 멘토가 없습니다.");
        }else {
            return ResponseEntity.ok(userBriefWithRates);
        }
    }

}
