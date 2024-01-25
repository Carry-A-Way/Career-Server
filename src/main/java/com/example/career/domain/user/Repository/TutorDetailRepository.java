package com.example.career.domain.user.Repository;

import com.example.career.domain.community.Dto.Brief.UserBriefWithRate;
import com.example.career.domain.user.Entity.TutorDetail;
import com.example.career.domain.user.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TutorDetailRepository extends JpaRepository<TutorDetail,Long> {
    public TutorDetail findByTutorId(Long id);
    // rateCount에 따라 내림차순으로 정렬하면서 키워드에 맞는 TutorDetail 찾기
    @Query("SELECT new com.example.career.domain.community.Dto.Brief.UserBriefWithRate(u, t) " +
            "FROM TutorDetail t INNER JOIN User u ON t.tutorId = u.id " +
            "WHERE (t.consultMajor1 LIKE %:keyword% OR t.consultMajor2 LIKE %:keyword% OR t.consultMajor3 LIKE %:keyword%) " +
            "ORDER BY t.rateCount DESC")
    Page<UserBriefWithRate> findByKeywordOrderByRateCountDesc(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT new com.example.career.domain.community.Dto.Brief.UserBriefWithRate(u, t) " +
            "FROM TutorDetail t INNER JOIN User u ON t.tutorId = u.id " +
            "WHERE (t.consultMajor1 LIKE %:keyword% OR t.consultMajor2 LIKE %:keyword% OR t.consultMajor3 LIKE %:keyword%) " +
            "ORDER BY t.rateAvg DESC")
    Page<UserBriefWithRate> findByKeywordOrderByRateAvgDesc(@Param("keyword") String keyword, Pageable pageable);
    @Query("SELECT new com.example.career.domain.community.Dto.Brief.UserBriefWithRate(u, t) " +
            "FROM TutorDetail t INNER JOIN User u ON t.tutorId = u.id " +
            "WHERE (t.tutorId = :userId) ")
    UserBriefWithRate findUserCardData(@Param("userId") Long userId);

    @Query("SELECT new com.example.career.domain.community.Dto.Brief.UserBriefWithRate(u, t) " +
            "FROM TutorDetail t INNER JOIN User u ON t.tutorId = u.id " +
            "ORDER BY u.createdAt DESC")
    Page<UserBriefWithRate> findByUserRecently(Pageable pageable);
}
