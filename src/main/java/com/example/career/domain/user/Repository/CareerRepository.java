package com.example.career.domain.user.Repository;

import com.example.career.domain.user.Entity.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CareerRepository extends JpaRepository<TestEntity,Long> {

}
