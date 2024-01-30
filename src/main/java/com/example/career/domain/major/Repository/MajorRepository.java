package com.example.career.domain.major.Repository;

import com.example.career.domain.major.Entity.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MajorRepository extends JpaRepository<Major,Long> {
    List<Major> findByMajorNameContainingOrderByMajorNameAsc(String majorName);
}
