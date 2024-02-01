package com.example.career.domain.user.Repository;

import com.example.career.domain.user.Entity.Career;
import com.example.career.domain.user.Entity.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CareerRepository extends JpaRepository<Career, Long> {
    public Optional<Career> findByTutorIdAndIdx(Long id, Long idx);
    public List<Career> findAllByTutorId(Long id);
    @Transactional
    public void deleteByTutorIdAndIdx(Long tutorId, Long idx);
}
