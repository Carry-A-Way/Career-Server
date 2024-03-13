package com.example.career.domain.oauth.Repository;

import com.example.career.domain.oauth.Entity.UserSns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSnsRepository extends JpaRepository<UserSns,Long> {
    UserSns findBySnsId(Long snsId);
}
