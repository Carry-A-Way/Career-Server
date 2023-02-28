package com.example.career.domain.user.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "StudentDetail")
public class StudentDetail {
    @Id
    @OneToOne
    @JoinColumn(name="studentId", referencedColumnName="id")
    private User user;

    private int interestingMajor1;

    private int interestingMajor2;

    private int interestingMajor3;

    @Column(nullable = false)
    private int credit20=0;

    @Column(nullable = false)
    private int credit40=0;
}
