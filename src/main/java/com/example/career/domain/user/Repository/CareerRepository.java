package com.example.career.domain.user.Repository;

import com.example.career.domain.user.Entity.Career;
import com.example.career.domain.user.Entity.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CareerRepository extends JpaRepository<Career, Long> {
    public Optional<Career> findByTutor_idAndIdx(Long id, Long idx);
    public List<Career> findByTutor_id(Long id);
}
