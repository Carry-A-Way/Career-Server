package com.example.career.domain.user.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "StudentDetail")
public class StudentDetail {
    @Id
    private Long student_id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private User user;

    @Column(nullable = false, columnDefinition = "varchar(50)")
    private String interestingMajor1;
    @Column(columnDefinition = "varchar(50)")
    private String interestingMajor2;
    @Column(columnDefinition = "varchar(50)")
    private String interestingMajor3;

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
