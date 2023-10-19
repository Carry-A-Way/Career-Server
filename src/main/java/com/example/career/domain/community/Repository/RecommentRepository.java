package com.example.career.domain.community.Repository;

import com.example.career.domain.community.Entity.Article;
import com.example.career.domain.community.Entity.Recomment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RecommentRepository extends JpaRepository<Recomment, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Recomment r SET r.content = :content, r.updatedAt = :updatedAt WHERE r.id = :id AND r.user.id = :userId")
    public void updateContentByIdAnduserId(@Param("id") Long id, @Param("content") String content, @Param("userId") Long userId, @Param("updatedAt") LocalDateTime updatedAt);

    @Transactional
    public void deleteByUserIdAndId(Long userId, Long id);

    @Modifying
    @Transactional
    @Query("UPDATE Recomment r SET r.heartCnt = r.heartCnt + 1 WHERE r.id = :id")
    public void incrementThumbsUpCnt(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("UPDATE Recomment r SET r.heartCnt = r.heartCnt - 1 WHERE r.id = :id")
    public void decrementThumbsUpCnt(@Param("id") Long id);

    List<Recomment> findByArticleId(Long articleId);
}
