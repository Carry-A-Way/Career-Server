package com.example.career.domain.major.Repository;

import com.example.career.domain.major.Entity.Major;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MajorRepository extends JpaRepository<Major,Long> {
    List<Major> findByMajorNameContaining(String majorName);

}
