package com.example.career.domain.consult.Repository;

import com.example.career.domain.consult.Entity.Consult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ConsultRepository extends JpaRepository<Consult, Long> {
    List<Consult> findAllByTutorId(Long tutorId);
}
