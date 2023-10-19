package com.example.career.domain.community.Controller;

import com.example.career.domain.community.Dto.request.RecommentDtoReq;
import com.example.career.domain.community.Entity.Recomment;
import com.example.career.domain.community.Service.RecommentService;
import com.example.career.domain.user.Entity.User;
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
        User user = (User) request.getAttribute("user");
        Long userId = user.getId();
        Recomment Recomment = recommentService.addRecomment(dto, userId);
        return ResponseEntity.ok(Recomment);
    }

    @Authenticated
    @PostMapping("/modify")
    public ResponseEntity<Object> modifyRecomment(@RequestBody RecommentDtoReq dto, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        Long userId = user.getId();
        recommentService.updateRecomment(dto, userId);
        return ResponseEntity.ok().build();
    }

    @Authenticated
    @DeleteMapping("delete")
    public ResponseEntity<Object> deleteRecomment(@RequestBody RecommentDtoReq dto, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        Long userId = user.getId();
        recommentService.deleteRecommentByUserIdAndId(userId, dto);
        return ResponseEntity.ok().build();
    }
}
