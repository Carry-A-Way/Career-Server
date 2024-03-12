package com.example.career.domain.user.Service;

import com.example.career.domain.user.Entity.StudentDetail;

public interface StudentDetailService {
    public StudentDetail getStudentDetailByStudentId(Long id);

    public int updateMyPoint(int delta, Long id);

    public int getMenteeMyPoint(Long id);
}
