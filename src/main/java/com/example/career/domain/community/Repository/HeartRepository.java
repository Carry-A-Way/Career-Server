package com.example.career.domain.community.Repository;

import com.example.career.domain.community.Entity.Article;
import com.example.career.domain.community.Entity.Heart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HeartRepository extends JpaRepository<Heart, Long> {
    @Query("SELECT a FROM Article a INNER JOIN Heart h ON a.id = h.typeId WHERE h.user.id = :userId AND h.type = :type")
    Page<Article> findArticlesByUserIdAndType(@Param("userId") Long userId, @Param("type") int type, Pageable pageable);

    void deleteByUserIdAndTypeIdAndType(Long userId, Long typeId, int type);

    List<Heart> findByUserIdAndType(Long userId, int type);
}
