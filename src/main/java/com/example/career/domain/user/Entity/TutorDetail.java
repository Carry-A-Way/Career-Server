package com.example.career.domain.user.Entity;

import com.example.career.domain.user.Repository.UserRepository;
import com.example.career.global.time.KoreaTime;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@DynamicUpdate
@Getter
@Table(name = "TutorDetail")
public class TutorDetail {
    @Id
    private Long tutorId; // user.getId()

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "tutor_id", referencedColumnName = "id")
//    private Long user; // user

    @Column(columnDefinition = "VARCHAR(15)")
    private String consultMajor1;

    @Column(columnDefinition = "VARCHAR(15)")
    private String consultMajor2;

    @Column(columnDefinition = "VARCHAR(15)")
    private String consultMajor3;

    @Column(nullable = false)
    private int cash = 0;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String portfileImg;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String consultMethod;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist // 데이터 생성이 이루어질때 사전 작업
    public void prePersist() {
        this.createdAt = KoreaTime.now();
        this.updatedAt = this.createdAt;
    }
}
