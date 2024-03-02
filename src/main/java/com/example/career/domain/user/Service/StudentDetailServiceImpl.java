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
    @Transactional
    public StudentDetail getStudentDetailByStudentId(Long id) {
        return studentDetailRepository.findByStudentId(id);
    };

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public int updateMyPoint(int delta, Long id) throws Exception {
        StudentDetail studentDetail = studentDetailRepository.findByStudentId(id);
        int point = studentDetail.getMyPoint();

        int nxtPoint = point + delta;
        if (nxtPoint >= 0) {
            studentDetail.setMyPoint(nxtPoint);
            studentDetailRepository.save(studentDetail);
            return nxtPoint;
        } else {
            throw new Exception("포인트가 부족합니다.");
        }
    }
}
