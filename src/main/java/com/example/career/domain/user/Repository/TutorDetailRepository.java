package com.example.career.domain.user.Repository;

import com.example.career.domain.user.Entity.TutorDetail;
import com.example.career.domain.user.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TutorDetailRepository extends JpaRepository<TutorDetail,Long> {
    public TutorDetail findByTutorId(Long id);
//    @Query("SELECT t FROM TutorDetail t WHERE t.consultMajor1 LIKE %:keyword% OR t.consultMajor2 LIKE %:keyword% OR t.consultMajor3 LIKE %:keyword%")
//    Page<TutorDetail> findByKeywordAndIsTutor(@Param("keyword") String keyword, Pageable pageable);
}
