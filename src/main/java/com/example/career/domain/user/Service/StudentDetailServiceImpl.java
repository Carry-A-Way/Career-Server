package com.example.career.domain.user.Service;

import com.example.career.domain.user.Entity.StudentDetail;
import com.example.career.domain.user.Repository.StudentDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StudentDetailServiceImpl implements StudentDetailService {
    private final StudentDetailRepository studentDetailRepository;
    @Override
    public StudentDetail getStudentDetailByStudentId(Long id) {
        return studentDetailRepository.findByStudentId(id);
    };

    @Override
    @Transactional
    public int updateMyPoint(int delta, Long id) {
        StudentDetail studentDetail = studentDetailRepository.findByStudentId(id);
        studentDetail.addDelta(delta);
        return studentDetail.getMyPoint();
    }

    @Override
    public int getMenteeMyPoint(Long id) {
        StudentDetail studentDetail = studentDetailRepository.findByStudentId(id);
        return studentDetail.getMyPoint();
    }
}
