package com.example.career.domain.user.Repository;

import com.example.career.domain.user.Entity.School;
import com.example.career.domain.user.Entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {
    public Optional<Tag> findByTutorIdAndIdx(Long id, Long idx);

    public List<Tag> findAllByTutorId(Long id);
}
