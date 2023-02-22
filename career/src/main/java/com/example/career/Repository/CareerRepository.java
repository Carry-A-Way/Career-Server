package com.example.career.Repository;

import com.example.career.Entity.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CareerRepository extends JpaRepository<TestEntity,Long> {

}
