package com.example.career.domain.user.Service;

public interface MentorCashService {
    int updateMentorCash(int delta, Long id) throws Exception;
    int getMentorCash(Long id);
}
