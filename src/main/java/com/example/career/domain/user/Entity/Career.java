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
@Table(name = "Career")
public class Career {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private Long tutor_id;

    @Column(nullable = false)
    private Long idx;

    @Column(columnDefinition = "VARCHAR(20)")
    private String careerType;

    @Column(columnDefinition = "VARCHAR(30)")
    private String careerName;

    @Column(columnDefinition = "VARCHAR(10)")
    private String startDate;
    @Column(columnDefinition = "VARCHAR(10)")
    private String endDate;
    @Column(columnDefinition = "VARCHAR(15)")
    private String state;
    @Column(columnDefinition = "VARCHAR(200)")
    private String content;

    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    @PrePersist // 데이터 생성이 이루어질때 사전 작업
    public void prePersist() {
        this.createAt = LocalDateTime.now();
        this.updateAt = this.createAt;
    }
}
