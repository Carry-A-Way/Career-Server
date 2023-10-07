package com.example.career.domain.community.Dto;

import com.example.career.domain.community.Entity.Article;
import com.example.career.domain.user.Entity.Career;
import jakarta.persistence.Column;
import lombok.*;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    public Article toArticleEntity(Long userId, String userNickname) {
        return Article.builder()
                .userId(userId)
                .userNickname(userNickname)
                .categoryId(categoryId)
                .title(title)
                .content(content)
                .img1(img1)
                .img2(img2)
                .img3(img3)
                .img4(img4)
                .img5(img5)
                .img6(img6)
                .build();

    }
}
