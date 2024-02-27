package com.example.career.domain.user.Repository;

import com.example.career.domain.user.Dto.MenteeRespDto;
import com.example.career.domain.user.Entity.FAQ;
import com.example.career.domain.user.Entity.MentorHeart;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MentorHeartRepository extends JpaRepository<MentorHeart,Long> {
     void deleteMentorHeartByMenteeIdAndAndMentorId(Long menteeId, Long mentorId);

     Boolean existsMentorHeartByMenteeIdAndAndMentorId(Long menteeId, Long mentorId);
     List<MentorHeart> findAllByMenteeIdOrderByCreatedAtDesc(Pageable pageable, Long mentorId);

}
