package com.example.career.domain.user.Service;

public interface MentorCashService {
    int updateMentorCash(int delta, Long id);
    int getMentorCash(Long id);
}
