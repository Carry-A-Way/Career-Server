package com.example.career.domain.user.Service;

import com.example.career.domain.user.Entity.Tag;
import com.example.career.domain.user.Repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService{

    private final TagRepository tagRepository;
    @Override
    public List<Tag> getTagByTutorId(Long id) {
        return tagRepository.findAllByTutorId(id);
    }
}
