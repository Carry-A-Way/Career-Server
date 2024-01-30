package com.example.career.domain.major.Service;

import com.example.career.domain.major.Entity.Major;
import com.example.career.domain.major.Repository.MajorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MajorServiceImpl implements MajorService{
    private final MajorRepository majorRepository;
    @Override
    public List<Major> getMajorListContaining(String majorName) {
        return majorRepository.findByMajorNameContainingOrderByMajorNameAsc(majorName);
    }
}
