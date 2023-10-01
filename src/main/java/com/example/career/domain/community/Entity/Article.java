package com.example.career.domain.community.Entity;


import com.example.career.domain.search.Dto.CommunitySearchRespDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "Article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long categoryId;

    @Column(columnDefinition = "VARCHAR(30)", nullable = false)

    private String title;

    @Column(columnDefinition = "MEDIUMTEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int heartCnt;

    @Column(nullable = false)
    @ColumnDefault("0") //default 0
    private int commentCnt;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String img1;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String img2;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String img3;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String img4;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String img5;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String img6;


    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public CommunitySearchRespDto toDto() {
        return CommunitySearchRespDto.builder()
                .id(id)
                .userId(userId)
                .categoryId(categoryId)
                .title(title)
                .content(content)
                .heartCnt(heartCnt)
                .commentCnt(commentCnt)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}
