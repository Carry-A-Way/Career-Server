package com.example.career.domain.consult.Repository;

import com.example.career.domain.consult.Entity.TutorSlot;
import com.example.career.domain.user.Entity.TutorDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TutorSlotRepository extends JpaRepository<TutorSlot, Long> {
    public TutorSlot findTutorSlotByTutorDetailAndConsultDate(TutorDetail tutorDetail, LocalDate consultDate);
    public List<TutorSlot> findAllByTutorDetail(TutorDetail tutorDetail);
}
