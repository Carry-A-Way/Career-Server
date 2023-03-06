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
@Table(name = "TutorDetail")
public class TutorDetail {
    @Id
    @OneToOne
    @JoinColumn(name="tutor_id", referencedColumnName="id")
    private User user;

    @Column(columnDefinition = "VARCHAR(15)", nullable = false)
    private String major1;

    @Column(columnDefinition = "VARCHAR(15)")
    private String major2;

    @Column(columnDefinition = "VARCHAR(15)")
    private String major3;

    @Column(columnDefinition = "VARCHAR(15)", nullable = false)
    private String consultingMajor1;

    @Column(columnDefinition = "VARCHAR(15)")
    private String consultingMajor2;

    @Column(columnDefinition = "VARCHAR(15)")
    private String consultingMajor3;

    @Column(columnDefinition = "JSON")
    private String career;

    @Column(nullable = false)
    private int cash = 0;

    @Lob
    private byte[] portfolio;

    @Column(columnDefinition = "MEDIUMTEXT", nullable = false)
    private String consultingMethod;

    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    @PrePersist // 데이터 생성이 이루어질때 사전 작업
    public void prePersist() {
        this.createAt = LocalDateTime.now();
        this.updateAt = this.createAt;
    }
}
