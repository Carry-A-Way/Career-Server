package com.example.career.domain.community.Repository;

import com.example.career.domain.community.Dto.response.ArticleCountByCategoryDto;
import com.example.career.domain.community.Entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Article a SET a.heartCnt = a.heartCnt + 1 WHERE a.id = :id")
    void incrementArticleThumbsUp(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("UPDATE Article a SET a.heartCnt = a.heartCnt - 1 WHERE a.id = :id")
    void decrementArticleThumbsUp(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("UPDATE Article a SET a.commentCnt = a.commentCnt + 1 WHERE a.id = :id")
    void incrementArticleCommentCnt(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("UPDATE Article a SET a.commentCnt = a.commentCnt - 1 WHERE a.id = :id")
    void decrementArticleCommentCnt(@Param("id") Long id);
    @Transactional
    void deleteByIdAndUserId(Long Id, Long userId);

    Optional<Article> findById(Long id);

    Page<Article> findByCategoryId(int categoryId, Pageable pageable);

    @Query("SELECT a FROM Article a WHERE a.user.id = :userId")
    Page<Article> findAllByUserId(Long userId, Pageable pageable);

    @Query("SELECT new com.example.career.domain.community.Dto.response.ArticleCountByCategoryDto(a.categoryId, COUNT(a)) FROM Article a GROUP BY a.categoryId")
    List<ArticleCountByCategoryDto> countArticlesByCategoryId();

    @Query("SELECT a FROM Article a WHERE a.title LIKE %:keyword% OR a.content LIKE %:keyword% " +
            "OR a.id IN (SELECT c.id FROM Comment c WHERE c.content LIKE %:keyword%) " +
            "OR a.id IN (SELECT r.id FROM Recomment r WHERE r.content LIKE %:keyword%)")
    List<Article> findAllBySearchKeyWord(@Param("keyword") String keyword, Pageable pageable);

    Page<Article> findAllByCategoryId(Long categoryId, Pageable pageable);
}
