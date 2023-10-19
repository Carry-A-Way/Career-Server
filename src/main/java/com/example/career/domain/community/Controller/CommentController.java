package com.example.career.domain.community.Controller;

import com.example.career.domain.community.Dto.response.CommentDto;
import com.example.career.domain.community.Dto.request.CommentDtoReq;
import com.example.career.domain.community.Entity.Comment;
import com.example.career.domain.community.Service.CommentService;
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
@RequestMapping("/community/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @Authenticated
    @GetMapping("my_comments")
    public ResponseEntity<List<CommentDto>> allCommentedArticles(HttpServletRequest request, @RequestParam int page, @RequestParam int size) {
        User user = (User) request.getAttribute("user");
        Long userId = user.getId();
        List<CommentDto> comments = commentService.allComments(userId, page, size);
        return ResponseEntity.ok(comments);
    }

    @Authenticated
    @PostMapping("/add")
    public ResponseEntity<Comment> addComment(@RequestBody CommentDtoReq commentDtoReq, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        Long userId = user.getId();
        Comment comment = commentService.addComment(commentDtoReq, userId);
        return ResponseEntity.ok(comment);
    }

    @Authenticated
    @PostMapping("/modify")
    public ResponseEntity<Object> modifyComment(@RequestBody CommentDtoReq commentDtoReq, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        Long userId = user.getId();
        commentService.updateComment(commentDtoReq, userId);
        return ResponseEntity.ok().build();
    }

    @Authenticated
    @DeleteMapping("delete")
    public ResponseEntity<Object> deleteComment(@RequestBody CommentDtoReq commentDtoReq, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        Long userId = user.getId();
        commentService.deleteCommentByUserIdAndId(userId, commentDtoReq);
        return ResponseEntity.ok().build();
    }
}
