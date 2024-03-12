package com.example.career.domain.user.Service;

import com.example.career.domain.user.Entity.TutorDetail;
import com.example.career.domain.user.Repository.TutorDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MentorCashServiceImpl implements MentorCashService {

    private final TutorDetailRepository tutorDetailRepository;

    @Override
    @Transactional
    public int updateMentorCash(int delta, Long id) {
        TutorDetail tutorDetail = tutorDetailRepository.findByTutorId(id);
        tutorDetail.addDelta(delta);
        return tutorDetail.getCash();
    }

    @Override
    public int getMentorCash(Long id) {
        TutorDetail tutorDetail = tutorDetailRepository.findByTutorId(id);
        return tutorDetail.getCash();
    }
}
