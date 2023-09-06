package com.example.career.domain.user.Service;

import com.example.career.domain.user.Entity.Tag;

import java.util.List;

public interface TagService {
    public List<Tag> getTagByTutorId(Long id);
}
