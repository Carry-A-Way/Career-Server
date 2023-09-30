package com.example.career.domain.community.Repository;

import com.example.career.domain.community.Dto.ArticleCountByCategoryDto;
import com.example.career.domain.community.Entity.Article;
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
import java.util.Map;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Article a SET a.heartCnt = a.heartCnt + 1 WHERE a.id = :id AND a.userId = :userId")
    void incrementArticleThumbsUp(@Param("id") Long id, @Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query("UPDATE Article a SET a.heartCnt = a.heartCnt - 1 WHERE a.id = :id AND a.userId = :userId")
    void decrementArticleThumbsUp(@Param("id") Long id, @Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query("UPDATE Article a SET a.commentCnt = a.commentCnt + 1 WHERE a.id = :id AND a.userId = :userId")
    void incrementArticleCommentCnt(@Param("id") Long id, @Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query("UPDATE Article a SET a.commentCnt = a.commentCnt - 1 WHERE a.id = :id AND a.userId = :userId")
    void decrementArticleCommentCnt(@Param("id") Long id, @Param("userId") Long userId);
    @Transactional
    void deleteByIdAndUserId(Long Id, Long userId);

    List<Article> findAllByTitleContainingOrContentContaining(String title, String content);

    Page<Article> findByCategoryId(int categoryId, Pageable pageable);

    @Query("SELECT new com.example.career.domain.community.Dto.ArticleCountByCategoryDto(a.categoryId, COUNT(a)) FROM Article a GROUP BY a.categoryId")
    List<ArticleCountByCategoryDto> countArticlesByCategoryId();

}
