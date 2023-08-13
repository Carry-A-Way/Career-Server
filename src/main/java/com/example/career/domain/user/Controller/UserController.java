package com.example.career.domain.user.Controller;

import com.example.career.domain.user.Dto.SignUpReqDto;
import com.example.career.domain.user.Dto.UserReqDto;
import com.example.career.domain.user.Entity.User;
import com.example.career.domain.user.Service.UserService;
import com.example.career.global.valid.ValidCheck;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import java.io.IOException;

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
    @PostMapping("signup/mentor")
    public ResponseEntity<SignUpReqDto> signUp(@RequestBody SignUpReqDto user) throws IOException {
        return ResponseEntity.ok(userService.signup(user));
    }
    @PostMapping("/signup")
    public ResponseEntity<SignUpReqDto> signup(@Valid @RequestBody SignUpReqDto userDto) {
        return ResponseEntity.ok(userService.signup(userDto));
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<SignUpReqDto> getMyUserInfo(HttpServletRequest request) {
        return ResponseEntity.ok(userService.getMyUserWithAuthorities());
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<SignUpReqDto> getUserInfo(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserWithAuthorities(username));
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
    // 프로필 이미지
    @PostMapping("file/profile")
    public String uploadProfileImage(@RequestParam("image") MultipartFile multipartFile) throws IOException {
        return userService.uploadProfile(multipartFile);
    }
    // 엑티브 이미지 여러장
    @PostMapping("file/active")
    public List<String> uploadActiveImages(@RequestParam("images") List<MultipartFile> multipartFiles) throws IOException {
        return userService.uploadActiceImages(multipartFiles);
    }
    @GetMapping("file/test")
    public String test()  {
        return "test";
    }


}