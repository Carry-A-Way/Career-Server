package com.example.career.domain.oauth.Service;

import com.example.career.domain.oauth.Entity.UserSns;
import org.springframework.transaction.annotation.Transactional;

public interface UserSnsService {
    @Transactional
    void snsSignup(Long userId, int type, Long snsId);

    UserSns findBySnsId(Long snsId);
}
