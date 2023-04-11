package com.example.career.domain.consult.Entity;

import com.example.career.domain.major.Entity.Major;
import com.example.career.domain.user.Entity.StudentDetail;
import com.example.career.domain.user.Entity.TutorDetail;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "Consult")
public class Consult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @OneToOne
//    @JoinColumn(name = "review_id", referencedColumnName = "id")
    private Long reviewId;

    @Column(columnDefinition = "MEDIUMTEXT",nullable = false)
    private String contentsUrl;

    @Column(columnDefinition = "MEDIUMTEXT",nullable = false)
    private String zoomLink;

    @Column(nullable = false)
    private int status = 0;

//    @ManyToOne
//    @JoinColumn(name = "tutor_id", referencedColumnName = "tutor_id")
    private Long tutorId;

//    @ManyToOne
//    @JoinColumn(name = "stu_id", referencedColumnName = "student_id")
    private Long stuId;

//    @ManyToOne
//    @JoinColumn(name = "major_id", referencedColumnName = "id")
    private String major;

    private LocalDateTime studentEnter;

    private LocalDateTime studentLeft;

    private LocalDateTime tutorEnter;

    private LocalDateTime tutorLeft;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    @PrePersist // 데이터 생성이 이루어질때 사전 작업
    public void prePersist() {
        this.createAt = LocalDateTime.now();
        this.updateAt = this.createAt;
    }

}
