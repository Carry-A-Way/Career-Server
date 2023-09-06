package com.example.career.domain.user.Repository;

import com.example.career.domain.user.Entity.TutorDetail;
import com.example.career.domain.user.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TutorDetailRepository extends JpaRepository<TutorDetail,Long> {
    public TutorDetail findByTutorId(Long id);
}
