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
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(30)", nullable = false)
    private String name;

    @Column(columnDefinition = "VARCHAR(30)", nullable = false)
    private String username;

    @Column(columnDefinition = "VARCHAR(30)", unique = true, nullable = false)
    private String nickname;

    @Column(columnDefinition = "CHAR(13)")
    private String telephone;

    @Column(nullable = false)
    private int authType = 1;

    @Column(columnDefinition = "MEDIUMTEXT", nullable = false)
    private String password;

    @Column(columnDefinition = "CHAR(7)", nullable = false)
    private String role = "USER";

    @Column(nullable = false)
    private int status = 0;

    @Column(nullable = false)
    private Boolean gender = true;

    @Lob
    private String profile_img;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String introduce;

    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    @PrePersist // 데이터 생성이 이루어질때 사전 작업
    public void prePersist() {
        this.createAt = LocalDateTime.now();
        this.updateAt = this.createAt;
    }

}
