package com.example.career.domain.user.Service;

import com.example.career.domain.user.Dto.SignUpReqDto;
import com.example.career.domain.user.Entity.User;
import com.example.career.domain.user.Repository.UserRepository;
import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;
    @Override
    public User signUp(SignUpReqDto signUpReqDto) {
        User user = signUpReqDto.toUserEntity();
        return userRepository.save(user);
    }
    @Override
    public boolean validUsername(String username) {

        if (userRepository.findByUsername(username)==null) return true;
        return false;
    }

    @Override
    public boolean validNickname(String nickname) {
        if (userRepository.findByNickname(nickname)==null) return true;
        return false;
    }
    @Override
    public boolean validTelephone(String telephone) {
        if (userRepository.findByTelephone(telephone)==null) return true;
        return false;
    }
}
