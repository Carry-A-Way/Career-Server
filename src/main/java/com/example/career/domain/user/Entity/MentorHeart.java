package com.example.career.domain.user.Entity;

import com.example.career.global.time.KoreaTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "mentor_heart",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"mentorId", "menteeId"})}
)
public class MentorHeart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long mentorId;

    @Column(nullable = false)
    private Long menteeId;

    private LocalDateTime createdAt;

    @PrePersist // 데이터 생성이 이루어질때 사전 작업
    public void prePersist() {
        this.createdAt = KoreaTime.now();
    }

}