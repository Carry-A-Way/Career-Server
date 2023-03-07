package com.example.career.domain.user.Repository;

import com.example.career.domain.user.Entity.StudentDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentDetailRepository extends JpaRepository<StudentDetail,Long> {
}
