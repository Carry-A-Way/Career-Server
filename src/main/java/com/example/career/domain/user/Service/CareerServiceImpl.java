package com.example.career.domain.user.Service;

import com.example.career.domain.user.Entity.Career;
import com.example.career.domain.user.Repository.CareerRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CareerServiceImpl implements CareerService{
    private final CareerRepository careerRepository;
    @Override
    public List<Career> getCareerByTutorId(Long id) {
        return careerRepository.findAllByTutorId(id);
    }
}
