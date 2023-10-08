package com.example.career.domain.community.Dto.Brief;


import com.example.career.domain.community.Entity.Article;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArticleBrief {
    private Long id;
    private String title;

    public ArticleBrief(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
    }
}