package com.example.career.domain.oauth.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "UserSns")
public class UserSns {
    @Id
    private Long id;

    @Column(nullable = false)
    private int type;

    @Column(nullable = false)
    private Long snsId;
}
