package com.example.career.domain.community.Controller;

import com.example.career.domain.community.Dto.CommentDto;
import com.example.career.domain.community.Dto.RecommentDto;
import com.example.career.domain.community.Entity.Comment;
import com.example.career.domain.community.Entity.Recomment;
import com.example.career.domain.community.Service.CommentService;
import com.example.career.domain.community.Service.RecommentService;
import com.example.career.global.annotation.Authenticated;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/community/recomment")
@RequiredArgsConstructor
public class RecommentController {
    private final RecommentService recommentService;

    @Authenticated
    @PostMapping("/add")
    public ResponseEntity<Recomment> addRecomment(@RequestBody RecommentDto recommentDto, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        String userNickname = (String) request.getAttribute("userNickname");
        Recomment Recomment = recommentService.addRecomment(recommentDto, userId, userNickname);
        return ResponseEntity.ok(Recomment);
    }

    @Authenticated
    @PostMapping("/modify")
    public ResponseEntity<Object> modifyRecomment(@RequestBody RecommentDto recommentDto, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        recommentService.updateRecomment(recommentDto, userId);
        return ResponseEntity.ok().build();
    }

    @Authenticated
    @DeleteMapping("delete")
    public ResponseEntity<Object> deleteRecomment(@RequestBody RecommentDto recommentDto, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        recommentService.deleteRecommentByUserIdAndId(userId, recommentDto.getId());;
        return ResponseEntity.ok().build();
    }
}
