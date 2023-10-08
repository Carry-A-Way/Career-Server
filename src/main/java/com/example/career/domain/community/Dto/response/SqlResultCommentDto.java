package com.example.career.domain.community.Dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SqlResultCommentDto {
    private Long id;
    private String content;
    private int heartCnt;
    private int recommentCnt;
    private LocalDateTime createdAt;

    // Fields for UserBrief
    private Long userId;
    private String userNickname;
    private Boolean userIsTutor;
    private String userProfileImg;

    // Fields for ArticleBrief
    private Long articleId;
    private String articleTitle;

}
