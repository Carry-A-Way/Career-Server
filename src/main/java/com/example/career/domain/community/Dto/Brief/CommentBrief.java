package com.example.career.domain.community.Dto.Brief;

import com.example.career.domain.community.Entity.Article;
import com.example.career.domain.community.Entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentBrief {
    private Long id;
    public CommentBrief(Comment comment) {
        this.id = comment.getId();
    }
}
