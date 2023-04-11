package com.example.career.domain.user.Controller;

import com.example.career.domain.user.Dto.SignUpReqDto;
import com.example.career.domain.user.Dto.UserReqDto;
import com.example.career.domain.user.Entity.User;
import com.example.career.domain.user.Service.UserService;
import com.example.career.global.valid.ValidCheck;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@AllArgsConstructor
public class UserController {

    private UserService userService;
    
    // 로그인
    @PostMapping("signin")
    public ValidCheck signIn(@RequestBody UserReqDto userReqDto) {
        User user = userService.signIn(userReqDto);
        ValidCheck validCheck = new ValidCheck(user);
        
        return validCheck;
    }

    // 회원가입 버튼
    // TODO : jwt token
    @PostMapping("signup")
    public ValidCheck signUp(@RequestBody SignUpReqDto signUpReqDto) {
        System.out.println(signUpReqDto.toString());
        ValidCheck validCheck;
        try {
            validCheck = new ValidCheck(userService.signUp(signUpReqDto));
        } catch (Exception e) {
            validCheck = new ValidCheck(null);
        }
        
        return validCheck;
    }
    //{"username": "ss", "temp": "sssss"}
    // username 중복확인
    @PostMapping("valid/username")
    public ValidCheck validUsername(@RequestBody SignUpReqDto signUpReqDto) {
        boolean isValid = userService.validUsername(signUpReqDto.getUsername());
        ValidCheck validCheck = new ValidCheck(isValid);
        
        return validCheck;
    }

    // nickname 중복확인
    @PostMapping("valid/nickname")
    public ValidCheck validNickname(@RequestBody SignUpReqDto signUpReqDto) {
        boolean isValid = userService.validNickname(signUpReqDto.getNickname());
        ValidCheck validCheck = new ValidCheck(isValid);

        return validCheck;
    }

    // telephone 중복확인
    @PostMapping("valid/telephone")
    public ValidCheck validTelephone(@RequestBody SignUpReqDto signUpReqDto) {
        boolean isValid = userService.validTelephone(signUpReqDto.getTelephone());
        ValidCheck validCheck = new ValidCheck(isValid);
        
        return validCheck;
    }

    //landing 홈페이지 /tutor/home

    @PostMapping("/tutor/home")
    public ValidCheck landingHomementor() { // jwt 토큰 인증

        return null;
    }

}