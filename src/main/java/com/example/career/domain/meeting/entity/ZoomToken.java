package com.example.career.domain.meeting.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "zoom_token")
public class ZoomToken {
    @Id
    Long id;

    @Column(columnDefinition = "MEDIUMTEXT")
    String accessToken;
    @Column(columnDefinition = "MEDIUMTEXT")
    String refreshToken;
    LocalDateTime updatedAt;
    @PrePersist // 데이터 생성이 이루어질때 사전 작업
    public void prePersist() {
        this.updatedAt = LocalDateTime.now();
    }
}
