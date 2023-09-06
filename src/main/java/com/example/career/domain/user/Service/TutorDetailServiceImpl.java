package com.example.career.domain.user.Service;

import com.example.career.domain.user.Entity.TutorDetail;
import com.example.career.domain.user.Repository.TutorDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TutorDetailServiceImpl implements TutorDetailService {
    private final TutorDetailRepository tutorDetailRepository;
    @Override
    public TutorDetail getTutorDetailByTutorId(Long id) {
        return tutorDetailRepository.findByTutorId(id);
    }
}
