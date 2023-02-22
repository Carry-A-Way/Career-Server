package com.example.career.domain.major.Repository;

import com.example.career.domain.major.Entity.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CareerRepository extends JpaRepository<TestEntity,Long> {

}
