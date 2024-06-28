package com.example.career.domain.oauth.Service;
import com.example.career.domain.oauth.Entity.UserSns;
import com.example.career.domain.oauth.Repository.SnsIdCheckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SnsIdCheckService {
    @Autowired
    private SnsIdCheckRepository snsCheckRepository;

    public boolean checkAndRegisterUser(Long snsId) {
        Optional<UserSns> user = snsCheckRepository.findBySnsId(snsId);
        if (user.isPresent()) {
            // 사용자 로그인 처리 로직
            return true;
        } else {
            // 사용자 등록 로직
            return false;
        }
    }
}