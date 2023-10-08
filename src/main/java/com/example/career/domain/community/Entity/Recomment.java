package com.example.career.domain.community.Entity;
import com.example.career.domain.user.Entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Recomment")
public class Recomment {
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

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int heartCnt;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commentId")
    private Comment comment;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }
}
