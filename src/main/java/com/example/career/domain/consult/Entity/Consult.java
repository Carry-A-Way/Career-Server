package com.example.career.domain.consult.Entity;

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

    @OneToOne
    @JoinColumn(name = "id")
    private Review review;

    @Column(columnDefinition = "MEDIUMTEXT",nullable = false)
    private String contentsUrl;

    @Column(columnDefinition = "MEDIUMTEXT",nullable = false)
    private String zoomLink;

    @Column(nullable = false)
    private int status = 0;

//    @ManyToOne
//    @JoinColumn(name = "tutor_id")
//    private TutorDetail tutorDetail;
//
//    @ManyToOne
//    @JoinColumn(name = "stu_id")
//    private StudentDetail studentDetail;
//
//    @ManyToOne
//    @JoinColumn(name = "id")
//    private Major major;

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
