package com.example.career.domain.community.Repository;

import com.example.career.domain.community.Entity.Article;
import com.example.career.domain.community.Entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Modifying
    @Query("UPDATE Comment c SET c.content = :content, c.updatedAt = :updatedAt WHERE c.id = :id AND c.userId = :userId")
    @Transactional
    public void updateContentByuserIdAndId(@Param("id") Long id, @Param("content") String content, @Param("userId") Long userId, @Param("updatedAt") LocalDateTime updatedAt);

    @Transactional
    public void deleteByUserIdAndId(Long UserId, Long id);

    @Query("SELECT DISTINCT a FROM Article a INNER JOIN Comment c ON a.id = c.articleId AND c.userId = :userId UNION SELECT DISTINCT a FROM Article a INNER JOIN Recomment r ON a.id = r.articleId AND r.userId = :userId ORDER BY a.createdAt DESC")
    List<Article> findArticlesByUserId(@Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query("UPDATE Comment c SET c.heartCnt = c.heartCnt + 1 WHERE c.id = :id AND c.userId = :userId")
    public void incrementThumbsUpCnt(@Param("id") Long id, @Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query("UPDATE Comment c SET c.heartCnt = c.heartCnt - 1 WHERE c.id = :id AND c.userId = :userId")
    public void decrementThumbsUpCnt(@Param("id") Long id, @Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query("UPDATE Comment c SET c.recommentCnt = c.recommentCnt + 1 WHERE c.id = :id AND c.userId = :userId")
    public void incrementRecommentCntByIdAndUserId(@Param("id") Long id, @Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query("UPDATE Comment c SET c.recommentCnt = c.recommentCnt - 1 WHERE c.id = :id AND c.userId = :userId")
    public void decrementRecommentCntByIdAndUserId(@Param("id") Long id, @Param("userId") Long userId);

    @Query("SELECT DISTINCT a FROM Article a " +
            "INNER JOIN Comment c ON a.id = c.articleId " +
            "WHERE c.content LIKE %:keyword%")
    List<Article> searchArticlesByCommentContent(String keyword);

}
