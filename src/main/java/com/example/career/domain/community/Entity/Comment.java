package com.example.career.domain.community.Entity;

import com.example.career.domain.community.Dto.CommentDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@NamedNativeQuery(
        name = "find_combined_comments_by_user_id",
        query =
                "SELECT * FROM (" +
                        "SELECT c.id, c.user_id, c.user_nickname, c.article_id, c.content, c.heart_cnt, c.recomment_cnt, a.title AS articleTitle, c.created_at " +
                        "FROM article a " +
                        "JOIN comment c ON a.id = c.article_id AND c.user_id = :userId " +
                        "UNION " +
                        "SELECT r.id, r.user_id, r.user_nickname, r.article_id, r.content, r.heart_cnt, 0 AS recomment_cnt, a.title AS articleTitle, r.created_at " +
                        "FROM article a " +
                        "JOIN recomment r ON a.id = r.article_id AND r.user_id = :userId" +
                        ") AS combined_results " +
                        "ORDER BY combined_results.created_at DESC " +
                        "LIMIT :limit OFFSET :offset", // <- LIMIT and OFFSET are added here
        resultSetMapping = "comment_dto_mapping"
)
@SqlResultSetMapping(
        name = "comment_dto_mapping",
        classes = @ConstructorResult(
                targetClass = CommentDto.class,
                columns = {
                        @ColumnResult(name = "id", type = Long.class),
                        @ColumnResult(name = "user_id", type = Long.class),
                        @ColumnResult(name = "user_nickname", type = String.class),
                        @ColumnResult(name = "article_id", type = Long.class),
                        @ColumnResult(name = "content", type = String.class),
                        @ColumnResult(name = "heart_cnt", type = Integer.class),
                        @ColumnResult(name = "recomment_cnt", type = Integer.class),
                        @ColumnResult(name = "articleTitle", type = String.class)
                }
        )
)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String userNickname;

    @Column(nullable = false)
    private Long articleId;

    @Column(columnDefinition = "MEDIUMTEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int heartCnt;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int recommentCnt;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }
}
