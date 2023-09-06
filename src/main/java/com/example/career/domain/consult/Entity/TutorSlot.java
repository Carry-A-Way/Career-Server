package com.example.career.domain.consult.Entity;

import com.example.career.domain.user.Entity.TutorDetail;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "TutorSlot")
public class TutorSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tutorId", referencedColumnName = "tutorId")
    private TutorDetail tutorDetail;
    // 상담 시작 날짜 및 시간
    @Column(nullable = false)
    private LocalDateTime startTime;

    // 상담 끝난 날짜 및 시간
    @Column(nullable = false)
    private LocalDateTime endTime;

    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    @PrePersist // 데이터 생성이 이루어질때 사전 작업
    public void prePersist() {
        this.createAt = LocalDateTime.now();
        this.updateAt = this.createAt;
    }
}
