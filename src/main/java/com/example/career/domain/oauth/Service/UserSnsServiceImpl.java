package com.example.career.domain.oauth.Service;

import com.example.career.domain.oauth.Entity.UserSns;
import com.example.career.domain.oauth.Repository.UserSnsRepository;
import com.example.career.domain.user.Exception.DuplicateMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserSnsServiceImpl implements UserSnsService {
    private final UserSnsRepository userSnsRepository;

    @Transactional
    @Override
    public void snsSignup(Long userId, int type, Long snsId) {
        if (userSnsRepository.findBySnsId(snsId) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
        }
        UserSns userSns = new UserSns(userId, type, snsId);
        userSnsRepository.save(userSns);
    }

    @Override
    public UserSns findBySnsId(Long snsId) {
        return userSnsRepository.findBySnsId(snsId);
    }
}
