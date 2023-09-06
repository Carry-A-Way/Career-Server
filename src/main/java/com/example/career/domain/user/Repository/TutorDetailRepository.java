package com.example.career.domain.user.Repository;

import com.example.career.domain.user.Entity.TutorDetail;
import com.example.career.domain.user.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TutorDetailRepository extends JpaRepository<TutorDetail,Long> {
    public Optional<TutorDetail> findByTutorId(Long id);
}
