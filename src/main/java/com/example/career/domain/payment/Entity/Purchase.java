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
@Table(name = "Purchase")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "stu_id", referencedColumnName = "studentId")
    private StudentDetail stuDetail;

    @ManyToOne
    @JoinColumn(name = "ticket_id", referencedColumnName = "id")
    private LessonTicket lessonTicket;
    @Column(nullable = false)
    private LocalDateTime date;
    @Column(nullable = false)
    private int amount_paid;
    @Column(nullable = false)
    private int status = 0;
    @Column(nullable = false)
    private int count =1;
    @Column(nullable = false)
    private int usedCount =0;
    @Column(nullable = false)
    private LocalDateTime expiredDate;


    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist // 데이터 생성이 이루어질때 사전 작업
    public void prePersist() {
        this.createdAt = KoreaTime.now();
        this.updatedAt = this.createdAt;
        this.date = this.createdAt;
    }
}
