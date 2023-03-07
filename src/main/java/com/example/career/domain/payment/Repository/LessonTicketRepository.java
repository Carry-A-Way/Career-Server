package com.example.career.domain.payment.Repository;

import com.example.career.domain.payment.Entity.LessonTicket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonTicketRepository extends JpaRepository<LessonTicket,Long> {
}
