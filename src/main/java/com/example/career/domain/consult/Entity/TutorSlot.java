package com.example.career.domain.consult.Entity;

import com.example.career.domain.user.Entity.TutorDetail;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
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

    // 멘토의 상담 가능 날짜
    @Column(nullable = false)
    private LocalDate consultDate;

    // 멘토가 상담 가능 시간대를 비트로 계산 24시간을 30분 단위 -> 48비트
    @ColumnDefault("0")
    private byte[] possibleTime; // byte 배열로 48비트 데이터 저장

    private LocalDateTime createAt;

    @PrePersist // 데이터 생성이 이루어질때 사전 작업
    public void prePersist() {
        this.createAt = LocalDateTime.now();
    }
}
