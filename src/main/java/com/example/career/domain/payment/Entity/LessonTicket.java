package com.example.career.domain.payment.Entity;

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
@Table(name = "LessonTicket")
public class LessonTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int duration;       // 수업시간

    @Column(columnDefinition = "varchar(30)",nullable = false)
    private String title;

    @Column(columnDefinition = "MEDIUMTEXT",nullable = false)
    private String describeTicket;



    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    @PrePersist // 데이터 생성이 이루어질때 사전 작업
    public void prePersist() {
        this.createAt = LocalDateTime.now();
        this.updateAt = this.createAt;
    }
}
