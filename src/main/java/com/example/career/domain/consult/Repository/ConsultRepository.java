package com.example.career.domain.consult.Repository;

import com.example.career.domain.consult.Entity.Consult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultRepository extends JpaRepository<Consult, Long> {
}
