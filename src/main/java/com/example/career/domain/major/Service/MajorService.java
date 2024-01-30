package com.example.career.domain.major.Service;

import com.example.career.domain.major.Entity.Major;
import org.springframework.stereotype.Service;

import java.util.List;

public interface MajorService {
    public List<Major> getMajorListContaining(String majorName);
}
