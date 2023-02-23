package com.example.career.domain.user.Controller;

import com.example.career.domain.user.Dto.SignUpReqDto;
import com.example.career.domain.user.Dto.UserResDto;
import com.example.career.domain.user.Dto.UserReqDto;
import com.example.career.domain.user.Dto.ValidRespDto;
import com.example.career.domain.user.Entity.User;
import com.example.career.domain.user.Service.UserService;
import com.example.career.global.valid.ValidCheck;

import lombok.AllArgsConstructor;

import java.sql.SQLIntegrityConstraintViolationException;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@AllArgsConstructor
public class UserController {

    private UserService userService;
    

    

    // 회원가입 버튼
    // TODO : jwt token
    @PostMapping("signin")
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
}