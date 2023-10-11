package com.example.career.domain.community.Dto.response;

import com.example.career.domain.community.Dto.Brief.UserBrief;
import com.example.career.domain.community.Entity.Article;
import com.example.career.domain.user.Entity.User;
import lombok.*;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto {
    private Long id;
    private Long categoryId;
    private String title;
    private String content;
    private List<String> removedImageUrls;
    private String img1;
    private String img2;
    private String img3;
    private String img4;
    private String img5;
    private String img6;
    private int heartCnt;
    private int commentCnt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isHeartClicked;
    private UserBrief user;

    public void setImgUrls(List<String> urlList) {
        if (urlList == null) return;

        for (int i = 0; i < urlList.size(); i++) {
            try {
                Field field = this.getClass().getDeclaredField("img" + (i + 1));
                field.set(this, urlList.get(i));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public Article toArticleEntity(User user) {
        return Article.builder()
                .user(user)
                .categoryId(categoryId)
                .title(title)
                .content(content)
                .img1(img1)
                .img2(img2)
                .img3(img3)
                .img4(img4)
                .img5(img5)
                .img6(img6)
                .heartCnt(heartCnt)
                .commentCnt(commentCnt)
                .build();

    }

    public static ArticleDto from(Article article) {
        if (article == null) return null;

        List<String> imageUrls = Arrays.asList(
                article.getImg1(),
                article.getImg2(),
                article.getImg3(),
                article.getImg4(),
                article.getImg5(),
                article.getImg6()
        );

        ArticleDto dto = ArticleDto.builder()
                .id(article.getId())
                .categoryId(article.getCategoryId())
                .title(article.getTitle())
                .content(article.getContent())
                .img1(article.getImg1())
                .img2(article.getImg2())
                .img3(article.getImg3())
                .img4(article.getImg4())
                .img5(article.getImg5())
                .img6(article.getImg6())
                .heartCnt(article.getHeartCnt())
                .commentCnt(article.getCommentCnt())
                .createdAt(article.getCreatedAt())
                .updatedAt(article.getUpdatedAt())
                .user(new UserBrief(article.getUser()))
                .build();

        dto.setImgUrls(imageUrls);

        return dto;
    }
}
