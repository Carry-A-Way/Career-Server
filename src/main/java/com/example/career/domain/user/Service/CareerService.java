package com.example.career.domain.user.Service;

import com.example.career.domain.user.Entity.Career;

import java.util.List;
import java.util.Optional;

public interface CareerService {
    public List<Career> getCareerByTutorId(Long id);
}
