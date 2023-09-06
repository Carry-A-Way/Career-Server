package com.example.career.domain.user.Entity;

import com.example.career.domain.user.Repository.UserRepository;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@DynamicUpdate
@Table(name = "School")
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private Long tutorId;

    @Column(nullable = false, unique = true)
    private Long idx;

    @Column(columnDefinition = "VARCHAR(15)")
    private String schoolType;

    @Column(columnDefinition = "VARCHAR(30)")
    private String schoolName;

    @Column(columnDefinition = "VARCHAR(10)")
    private String startDate;

    @Column(columnDefinition = "VARCHAR(10)")
    private String endDate;

    @Column(columnDefinition = "VARCHAR(15)")
    private String state; // 졸업, 졸업예정 등

    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    @PrePersist // 데이터 생성이 이루어질때 사전 작업
    public void prePersist() {
        this.createAt = LocalDateTime.now();
        this.updateAt = this.createAt;
    }
}
