package com.example.career.domain.user.Repository;

import com.example.career.domain.user.Entity.School;
import com.example.career.domain.user.Entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {
    public Optional<Tag> findByUserIdAndIdx(Long id, Long idx);

    public List<Tag> findAllByUserId(Long id);
    @Transactional
    public void deleteByUserIdAndIdx(Long userId, Long idx);
}
