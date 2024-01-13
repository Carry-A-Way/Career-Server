package com.example.career.domain.consult.Entity;

import com.example.career.domain.consult.Dto.ReviewRespDto;
import com.example.career.domain.user.Entity.StudentDetail;
import com.example.career.domain.user.Entity.TutorDetail;
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
@Table(name = "Review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long consultId;

    @Column(nullable = false)
    private int rate;

    @Column(columnDefinition = "MEDIUMTEXT",nullable = false)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "tutorId", referencedColumnName = "tutorId")
    private TutorDetail tutorDetail;
    @ManyToOne
    @JoinColumn(name = "studentId", referencedColumnName = "studentId")
    private StudentDetail studentDetail;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist // 데이터 생성이 이루어질때 사전 작업
    public void prePersist() {
        this.createdAt = KoreaTime.now();
        this.updatedAt = this.createdAt;
    }

    public ReviewRespDto toRespDto() {
        return ReviewRespDto.builder()
                .id(id)
                .consultId(consultId)
                .rate(rate)
                .comment(comment)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}
