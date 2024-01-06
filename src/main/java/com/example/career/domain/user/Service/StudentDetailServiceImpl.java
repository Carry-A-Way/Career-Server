package com.example.career.domain.user.Service;

import com.example.career.domain.user.Entity.StudentDetail;
import com.example.career.domain.user.Repository.StudentDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentDetailServiceImpl implements StudentDetailService {
    private final StudentDetailRepository studentDetailRepository;
    @Override
    public StudentDetail getStudentDetailByStudentId(Long id) {
        return studentDetailRepository.findByStudentId(id);
    };
}
