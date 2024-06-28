package com.example.career.domain.oauth.Repository;

import com.example.career.domain.oauth.Entity.UserSns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SnsIdCheckRepository extends JpaRepository<UserSns, Long> {
    Optional<UserSns> findBySnsId(Long snsId);
}
