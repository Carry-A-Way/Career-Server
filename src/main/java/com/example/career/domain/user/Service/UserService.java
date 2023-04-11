package com.example.career.domain.user.Service;

import com.example.career.domain.user.Dto.MentorHomeRespDto;
import com.example.career.domain.user.Dto.UserReqDto;
import com.example.career.domain.user.Dto.SignUpReqDto;
import com.example.career.domain.user.Entity.User;

public interface UserService {
    public User signIn(UserReqDto userReqDto);
    public User signUp(SignUpReqDto signUpReqDto);
    public boolean validUsername(String username);
    public boolean validNickname(String nickname);
    public boolean validTelephone(String telephone);
    public MentorHomeRespDto mentorHome();

}
