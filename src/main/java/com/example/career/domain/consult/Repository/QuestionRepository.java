package com.example.career.domain.consult.Repository;

import com.example.career.domain.consult.Entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question,Long> {
}
