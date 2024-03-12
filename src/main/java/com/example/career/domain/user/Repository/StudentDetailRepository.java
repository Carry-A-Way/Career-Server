package com.example.career.domain.user.Repository;

import com.example.career.domain.user.Entity.StudentDetail;
import com.example.career.domain.user.Entity.TutorDetail;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface StudentDetailRepository extends JpaRepository<StudentDetail,Long> {

    public StudentDetail findByStudentId(Long id);

    @Transactional
    public void deleteByStudentId(Long id);
}
