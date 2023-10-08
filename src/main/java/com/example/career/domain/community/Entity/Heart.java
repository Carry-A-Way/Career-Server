package com.example.career.domain.community.Entity;

import com.example.career.domain.user.Entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "Heart", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"userId", "typeId", "type"})
})
public class Heart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY) // 지연 로딩 사용
    @JoinColumn(name = "userId", referencedColumnName = "id") // 외래 키 칼럼 설정
    private User user;

    @Column(nullable = false)
    private Long typeId;

    @Column(nullable = false)
    private int type;

}
