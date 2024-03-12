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
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public int updateMentorCash(int delta, Long id) throws Exception {
        TutorDetail tutorDetail = tutorDetailRepository.findByTutorId(id);
        int cash = tutorDetail.getCash();

        int nxtCash = cash + delta;
        if (nxtCash >= 0) {
            tutorDetail.setCash(nxtCash);
            tutorDetailRepository.save(tutorDetail);
            return nxtCash;
        } else {
            throw new Exception("잔여 캐시가 부족합니다.");
        }
    }

    @Override
    public int getMentorCash(Long id) {
        TutorDetail tutorDetail = tutorDetailRepository.findByTutorId(id);
        return tutorDetail.getCash();
    }
}
