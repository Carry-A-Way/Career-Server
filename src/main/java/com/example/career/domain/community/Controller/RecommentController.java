package com.example.career.domain.community.Controller;

import com.example.career.domain.community.Dto.request.RecommentDtoReq;
import com.example.career.domain.community.Entity.Recomment;
import com.example.career.domain.community.Service.RecommentService;
import com.example.career.global.annotation.Authenticated;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/community/recomment")
@RequiredArgsConstructor
public class RecommentController {
    private final RecommentService recommentService;

    @Authenticated
    @PostMapping("/add")
    public ResponseEntity<Recomment> addRecomment(@RequestBody RecommentDtoReq dto, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        String userNickname = (String) request.getAttribute("nickname");
        Boolean isTutor = (Boolean) request.getAttribute("isTutor");
        Recomment Recomment = recommentService.addRecomment(dto, userId, userNickname, isTutor);
        return ResponseEntity.ok(Recomment);
    }

    @Authenticated
    @PostMapping("/modify")
    public ResponseEntity<Object> modifyRecomment(@RequestBody RecommentDtoReq dto, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        recommentService.updateRecomment(dto, userId);
        return ResponseEntity.ok().build();
    }

    @Authenticated
    @DeleteMapping("delete")
    public ResponseEntity<Object> deleteRecomment(@RequestBody RecommentDtoReq dto, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        recommentService.deleteRecommentByUserIdAndId(userId, dto.getId());;
        return ResponseEntity.ok().build();
    }
}
