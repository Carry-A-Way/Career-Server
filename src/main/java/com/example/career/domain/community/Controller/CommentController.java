package com.example.career.domain.community.Controller;

import com.example.career.domain.community.Dto.ArticleDto;
import com.example.career.domain.community.Dto.CommentDto;
import com.example.career.domain.community.Entity.Article;
import com.example.career.domain.community.Entity.Comment;
import com.example.career.domain.community.Service.CommentService;
import com.example.career.global.annotation.Authenticated;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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
        Long userId = (Long) request.getAttribute("userId");
        List<CommentDto> comments = commentService.allComments(userId, page, size);
        return ResponseEntity.ok(comments);
    }

    @Authenticated
    @PostMapping("/add")
    public ResponseEntity<Comment> addComment(@RequestBody CommentDto commentDto, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        Comment comment = commentService.addComment(commentDto, userId);
        return ResponseEntity.ok(comment);
    }

    @Authenticated
    @PostMapping("/modify")
    public ResponseEntity<Object> modifyComment(@RequestBody CommentDto commentDto, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        commentService.updateComment(commentDto, userId);
        return ResponseEntity.ok().build();
    }

    @Authenticated
    @DeleteMapping("delete")
    public ResponseEntity<Object> deleteComment(@RequestBody CommentDto commentDto, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        commentService.deleteCommentByUserIdAndId(userId, commentDto.getId());
        return ResponseEntity.ok().build();
    }
}
