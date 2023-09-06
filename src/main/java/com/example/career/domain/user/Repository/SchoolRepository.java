package com.example.career.domain.user.Repository;

import com.example.career.domain.user.Entity.School;
import com.example.career.domain.user.Entity.Tag;
import com.example.career.domain.user.Entity.TutorDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SchoolRepository extends JpaRepository<School,Long> {
    public Optional<School> findByTutor_idAndIdx(Long id, Long idx);
    public List<School> findByTutor_id(Long id);
}
