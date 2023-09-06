package com.example.career.domain.user.Service;
import com.example.career.domain.user.Entity.School;

import java.util.List;

public interface SchoolService {
    public List<School> getSchoolByTutorId(Long id);
}
