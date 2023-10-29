package com.example.career.domain.major.Entity;

import com.example.career.global.time.KoreaTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "Major")
public class Major {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(15)",nullable = false)
    private String name;

    @Column(columnDefinition = "MEDIUMTEXT", nullable = false)
    private String describeMajor;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist // 데이터 생성이 이루어질때 사전 작업
    public void prePersist() {
        this.createdAt = KoreaTime.now();
        this.updatedAt = this.createdAt;
    }
}
