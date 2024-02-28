package com.example.career.domain.user.Service;

import com.example.career.domain.user.Dto.WageReqDto;
import com.example.career.domain.user.Entity.TutorDetail;
import com.example.career.domain.user.Repository.TutorDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MentorWageServiceImpl implements MentorWageService {
    private final TutorDetailRepository tutorDetailRepository;

    @Override
    public void updateMentorWage(WageReqDto wageReqDto, Long id) {
        TutorDetail tutorDetail = tutorDetailRepository.findByTutorId(id);
        tutorDetail.setWage(wageReqDto.getWage());
//        tutorDetailRepository.update(tutorDetail);
        tutorDetailRepository.save(tutorDetail);
    }
}
