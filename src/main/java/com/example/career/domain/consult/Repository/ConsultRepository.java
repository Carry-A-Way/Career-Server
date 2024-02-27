package com.example.career.domain.consult.Repository;

import com.example.career.domain.consult.Entity.Consult;
import com.example.career.domain.user.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface ConsultRepository extends JpaRepository<Consult, Long> {
    List<Consult> findAllByMentor(User mentor);
    List<Consult> findAllByMentee(User mentee);
    List<Consult> findAllByMentorAndStatus(User mentor, int status);

    @Query("SELECT c FROM Consult c WHERE c.mentor.id = :mentorId AND c.status <> 2 " +
            "AND ((c.startTime <= :startTime AND c.endTime > :startTime) OR " +
            "(c.startTime < :endTime AND c.endTime >= :endTime) OR " +
            "(c.startTime >= :startTime AND c.endTime <= :endTime))")
    List<Consult> findOverlappingConsults(@Param("mentorId") Long mentorId,
                                          @Param("startTime") LocalDateTime startTime,
                                          @Param("endTime") LocalDateTime endTime);
    List<Consult> findByStatusAndStartTimeBefore(int status, LocalDateTime startTime);


}
