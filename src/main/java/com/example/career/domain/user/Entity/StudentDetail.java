package com.example.career.domain.user.Entity;

import com.example.career.global.time.KoreaTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@DynamicUpdate
@Table(name = "StudentDetail")
public class StudentDetail {
    @Id
    private Long studentId;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "studentId", referencedColumnName = "id")
//    private Long user_id;

    @Column(columnDefinition = "varchar(50)")
    private String interestingMajor1;
    @Column(columnDefinition = "varchar(50)")
    private String interestingMajor2;
    @Column(columnDefinition = "varchar(50)")
    private String interestingMajor3;

    @Column(nullable = false)
    private int credit20=0;

    @Column(nullable = false)
    private int credit40=0;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist // 데이터 생성이 이루어질때 사전 작업
    public void prePersist() {
        this.createdAt = KoreaTime.now();
        this.updatedAt = this.createdAt;
    }
}
