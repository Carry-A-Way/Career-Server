package com.example.career.domain.user.Repository;

import com.example.career.domain.user.Entity.StudentDetail;
import com.example.career.domain.user.Entity.TutorDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentDetailRepository extends JpaRepository<StudentDetail,Long> {
    public StudentDetail findByStudentId(Long id);
}
