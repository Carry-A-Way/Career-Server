package com.example.career.domain.consult.Repository;

import com.example.career.domain.consult.Entity.Review;
import com.example.career.domain.user.Entity.TutorDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    List<Review> findAllByTutorDetailOrderByCreatedAt(TutorDetail tutorDetail);
}
