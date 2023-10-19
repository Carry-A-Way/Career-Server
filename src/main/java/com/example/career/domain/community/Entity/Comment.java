package com.example.career.domain.community.Entity;

import com.example.career.domain.community.Dto.response.SqlResultCommentDto;
import com.example.career.domain.user.Entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NamedNativeQuery(
        name = "find_combined_comments_by_user_id",
        query =
                "SELECT * FROM (" +
                        "SELECT c.id, c.user_id, u.nickname AS user_nickname, u.is_tutor, u.profile_img, c.article_id, c.content, c.heart_cnt, c.recomment_cnt, a.title AS articleTitle, c.created_at, -1 AS commentId " +
                        "FROM article a " +
                        "JOIN comment c ON a.id = c.article_id " +
                        "JOIN user u ON c.user_id = u.id AND u.id = :userId " +
                        "UNION " +
                        "SELECT r.id, r.user_id, u.nickname AS user_nickname, u.is_tutor, u.profile_img, r.article_id, r.content, r.heart_cnt, 0 AS recomment_cnt, a.title AS articleTitle, r.created_at, c.id AS commentId " +
                        "FROM article a " +
                        "JOIN recomment r ON a.id = r.article_id " +
                        "JOIN comment c ON r.comment_id = c.id " +
                        "JOIN user u ON r.user_id = u.id AND u.id = :userId" +
                        ") AS combined_results " +
                        "ORDER BY combined_results.created_at DESC " +
                        "LIMIT :limit OFFSET :offset", // <- LIMIT and OFFSET are added here
        resultSetMapping = "comment_dto_mapping"
)
@SqlResultSetMapping(
        name = "comment_dto_mapping",
        classes = @ConstructorResult(
                targetClass = SqlResultCommentDto.class,
                columns = {
                        @ColumnResult(name = "id", type = Long.class),
                        @ColumnResult(name = "content", type = String.class),
                        @ColumnResult(name = "heart_cnt", type = Integer.class),
                        @ColumnResult(name = "recomment_cnt", type = Integer.class),
                        @ColumnResult(name = "created_at", type = LocalDateTime.class),
                        @ColumnResult(name = "user_id", type = Long.class),
                        @ColumnResult(name = "user_nickname", type = String.class),
                        @ColumnResult(name = "is_tutor", type = Boolean.class),
                        @ColumnResult(name = "profile_img", type = String.class),
                        @ColumnResult(name = "article_id", type = Long.class),
                        @ColumnResult(name = "articleTitle", type = String.class),
<<<<<<< HEAD
                        @ColumnResult(name= "commentId", type = Long.class)
=======
                        @ColumnResult(name= "isComment", type = Boolean.class)
>>>>>>> main
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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY) // 지연 로딩 사용
    @JoinColumn(name = "userId", referencedColumnName = "id") // 외래 키 칼럼 설정
    private User user;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY) // 지연 로딩 사용
    @JoinColumn(name = "articleId", referencedColumnName = "id") // 외래 키 칼럼 설정
    private Article article;

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

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Recomment> recomments = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }
}
