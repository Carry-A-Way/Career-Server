package com.example.career.domain.user.Repository;

import com.example.career.global.security.MOpOperator;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository2 extends JpaRepository<MOpOperator,Long> {

    @EntityGraph(attributePaths = "authorities")
    Optional<MOpOperator> findOneWithAuthoritiesByUsername(String loginId);
}
