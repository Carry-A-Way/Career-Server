package com.example.career.domain.major.Entity;

import com.example.career.domain.user.Entity.TutorDetail;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "TutorMajorDetail")
public class TutorMajorDetail { // 전공 소개 댓글
    @Id
    @Column(name = "major_detail_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "major_id", referencedColumnName = "id")
    private Major major;

    @ManyToOne
    @JoinColumn(name = "tutorId", referencedColumnName = "tutorId")
    private TutorDetail tutorDetail;

    @Column(columnDefinition = "MEDIUMTEXT", nullable = false)
    private String comment;

    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    @PrePersist // 데이터 생성이 이루어질때 사전 작업
    public void prePersist() {
        this.createAt = LocalDateTime.now();
        this.updateAt = this.createAt;
    }

}
