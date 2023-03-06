package com.example.career.domain.user.Entity;

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
@Table(name = "StudentDetail")
public class StudentDetail {
    @Id
    @OneToOne
    @JoinColumn(name="student_id", referencedColumnName="id")
    private User user;

    private int interestingMajor1;

    private int interestingMajor2;

    private int interestingMajor3;

    @Column(nullable = false)
    private int credit20=0;

    @Column(nullable = false)
    private int credit40=0;

    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    @PrePersist // 데이터 생성이 이루어질때 사전 작업
    public void prePersist() {
        this.createAt = LocalDateTime.now();
        this.updateAt = this.createAt;
    }
}
