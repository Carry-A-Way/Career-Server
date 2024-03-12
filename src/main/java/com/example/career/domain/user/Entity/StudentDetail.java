package com.example.career.domain.user.Entity;

import com.example.career.global.time.KoreaTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
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
    @ColumnDefault("0")
    private int myPoint;

    @Version
    @Column(name = "optlock", columnDefinition = "integer DEFAULT 0", nullable = false)
    private long version = 0L;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist // 데이터 생성이 이루어질때 사전 작업
    public void prePersist() {
        this.createdAt = KoreaTime.now();
        this.updatedAt = this.createdAt;
    }

    public void addDelta(int delta) {
        validDelta(delta);
        this.myPoint += delta;
    }

    private void validDelta(int delta) {
        if (this.myPoint + delta < 0) {
            throw new IllegalArgumentException();
        }
    }
}
