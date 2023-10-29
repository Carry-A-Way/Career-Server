package com.example.career.domain.payment.Entity;

import com.example.career.domain.user.Entity.StudentDetail;
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
@Entity
@Builder
@Table(name = "PurchaseUsed")
public class PurchaseUsed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDateTime usedDate;

    @ManyToOne
    @JoinColumn(name = "stu_id", referencedColumnName = "student_id")
    private StudentDetail stuDetail;

    @ManyToOne
    @JoinColumn(name = "purchase_id", referencedColumnName = "id")
    private Purchase purchase;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist // 데이터 생성이 이루어질때 사전 작업
    public void prePersist() {
        this.createdAt = KoreaTime.now();
        this.updatedAt = this.createdAt;
        this.usedDate = this.createdAt;
    }
}
