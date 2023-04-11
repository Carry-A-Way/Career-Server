package com.example.career.domain.user.Service;

import com.example.career.domain.consult.Repository.ConsultRepository;
import com.example.career.domain.user.Dto.MentorHomeRespDto;
import com.example.career.domain.user.Dto.UserReqDto;
import com.example.career.domain.user.Dto.SignUpReqDto;
import com.example.career.domain.user.Entity.User;
import com.example.career.domain.user.Repository.UserRepository;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final ConsultRepository consultRepository;
    @Override
    public User signIn(UserReqDto userReqDto) {
        return userRepository.findByUsernameAndPassword(userReqDto.getUsername(), userReqDto.getPassword());
    }
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

    @Override
    public MentorHomeRespDto mentorHome() {
        // 유저부터
        Long userid = 10L;
        // 유저 정보부터 가져오자
        MentorHomeRespDto mentorHomeRespDto = new MentorHomeRespDto();
        mentorHomeRespDto = userRepository.findById(userid).get().toHomeDto();

        // Consulting 정보





        return null;
    }
}
