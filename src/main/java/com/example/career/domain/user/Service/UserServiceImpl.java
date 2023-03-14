package com.example.career.domain.user.Service;

import com.example.career.domain.user.Dto.TutorSignUpReqDto;
import com.example.career.domain.user.Dto.UserReqDto;
import com.example.career.domain.user.Dto.SignUpReqDto;
import com.example.career.domain.user.Entity.TutorDetail;
import com.example.career.domain.user.Entity.User;
import com.example.career.domain.user.Repository.TutorDetailRepository;
import com.example.career.domain.user.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;
    @Override
    public User signIn(UserReqDto userReqDto) {
        return userRepository.findByUsernameAndPassword(userReqDto.getUsername(), userReqDto.getPassword());
    }
    @Override
    @Transactional
    public User signUp(TutorSignUpReqDto tutorSignUpReqDto) {
        User user = tutorSignUpReqDto.toUserEntity();
        TutorDetail tutorDetail = tutorSignUpReqDto.toTutorDetailEntity();
        TutorDetailRepository.save(tutorDetail);
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
