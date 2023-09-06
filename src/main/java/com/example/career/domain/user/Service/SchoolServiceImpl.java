package com.example.career.domain.user.Service;

import com.example.career.domain.user.Entity.School;
import com.example.career.domain.user.Repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolServiceImpl implements SchoolService{
    private final SchoolRepository schoolRepository;

    @Override
    public List<School> getSchoolByTutorId(Long id) {
        return schoolRepository.findAllByTutorId(id);
    }
}
