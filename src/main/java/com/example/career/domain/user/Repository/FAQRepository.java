package com.example.career.domain.user.Repository;

import com.example.career.domain.user.Entity.FAQ;
import com.example.career.domain.user.Entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface FAQRepository extends JpaRepository<FAQ,Long> {
    public List<FAQ> findAllByTutorIdOrderById(Long tutorId);
}
