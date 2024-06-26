package com.example.career.domain.community.Dto.response;

import com.example.career.domain.community.Dto.Brief.UserBrief;
import com.example.career.domain.community.Entity.Article;
import com.example.career.domain.user.Entity.User;
import com.example.career.global.aop.LoggingAspect;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private List<String> imgs;
    private int heartCnt;
    private int commentCnt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isHeartClicked;
    private UserBrief user;
    private String majors; // "수학과,수학교육과" ,로 분리

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    public void setImgUrls(List<String> urlList) {
        if (urlList == null) return;

        this.imgs = new ArrayList<>(urlList);
    }


    public Article toArticleEntity(User user) {
        Article.ArticleBuilder builder = Article.builder()
                .user(user)
                .categoryId(categoryId)
                .title(title)
                .majors(majors)
                .content(content)
                .heartCnt(heartCnt)
                .commentCnt(commentCnt);

        if (imgs != null) {
            for (int i = 0; i < imgs.size(); i++) {
                switch (i) {
                    case 0: builder.img1(imgs.get(i)); break;
                    case 1: builder.img2(imgs.get(i)); break;
                    case 2: builder.img3(imgs.get(i)); break;
                    case 3: builder.img4(imgs.get(i)); break;
                    case 4: builder.img5(imgs.get(i)); break;
                    case 5: builder.img6(imgs.get(i)); break;
                }
            }
        }

        return builder.build();
    }


    public static ArticleDto from(Article article) {
        if (article == null) return null;

        List<String> imageUrls = new ArrayList<>();
        if (article.getImg1() != null) imageUrls.add(article.getImg1());
        if (article.getImg2() != null) imageUrls.add(article.getImg2());
        if (article.getImg3() != null) imageUrls.add(article.getImg3());
        if (article.getImg4() != null) imageUrls.add(article.getImg4());
        if (article.getImg5() != null) imageUrls.add(article.getImg5());
        if (article.getImg6() != null) imageUrls.add(article.getImg6());


        ArticleDto dto = ArticleDto.builder()
                .id(article.getId())
                .categoryId(article.getCategoryId())
                .title(article.getTitle())
                .content(article.getContent())
                .heartCnt(article.getHeartCnt())
                .commentCnt(article.getCommentCnt())
                .majors(article.getMajors())
                .createdAt(article.getCreatedAt())
                .updatedAt(article.getUpdatedAt())
                .imgs(imageUrls)
                .user(new UserBrief(article.getUser()))
                .build();

//        dto.setImgUrls(imageUrls);

        return dto;
    }
}
