package com.example.career.domain.consult.Entity;

import com.example.career.domain.user.Entity.TutorDetail;
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
@Table(name = "Review")
public class Review {

    @Id
    private Long id;

    @Column(nullable = false)
    private int rate;

    @Column(columnDefinition = "MEDIUMTEXT",nullable = false)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "tutor_id", referencedColumnName = "tutor_id")
    private TutorDetail tutorDetail;

    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    @PrePersist // 데이터 생성이 이루어질때 사전 작업
    public void prePersist() {
        this.createAt = LocalDateTime.now();
        this.updateAt = this.createAt;
    }
}
