package com.example.career.domain.oauth.Controller;

import com.example.career.domain.oauth.Dto.SnsSignUpReqDto;
import com.example.career.domain.oauth.Entity.UserSns;
import com.example.career.domain.oauth.Repository.UserSnsRepository;
import com.example.career.domain.oauth.Service.KakaoService;
import com.example.career.domain.oauth.Service.SnsIdCheckService;
import com.example.career.domain.oauth.Service.UserSnsService;
import com.example.career.domain.user.Dto.LoginDto;
import com.example.career.domain.user.Dto.SignUpReqDto;
import com.example.career.domain.user.Dto.TokenDto;
import com.example.career.domain.user.Entity.User;
import com.example.career.domain.user.Service.UserService;
import com.example.career.global.jwt.JwtFilter;
import com.example.career.global.jwt.TokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("oauth")
@RequiredArgsConstructor
@Log4j
public class KakaoController {
    private final RestTemplate restTemplate;
    private final KakaoService kakaoService;
    private final SnsIdCheckService snsIdCheckService;
    private final UserSnsRepository userSnsRepository;
    private final UserSnsService userSnsService;
    private final UserService userService;

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Value("${api.base-url}")
    private String baseUrl;

//    private final MemberService memberService;

    @Transactional
//    @PostMapping("kakao_signup")
//    public ResponseEntity<Object> signUpWithKakao(@RequestBody SnsSignUpReqDto snsSignUpReqDto) {
//        SignUpReqDto signUpReqDto = snsSignUpReqDto.toSignUpReqDto();
//        SignUpReqDto res = null;
//
//        if (snsSignUpReqDto.getIsTutor() == true) { // 튜터의 경우
//            res = userService.signupTutor(signUpReqDto, true);
//        } else { // 튜티의 경우
//            res = userService.signupStudent(signUpReqDto, true);
//        }
//        userSnsService.snsSignup(res.getId(), 0, snsSignUpReqDto.getSnsId());
//
//        return ResponseEntity.ok(snsSignUpReqDto);
//    }

    @GetMapping("kakao/callback")
    public ResponseEntity<Object> redirectkakao(@RequestParam String code, HttpSession session) throws Exception {
        System.out.println("code:: " + code);

        // 접속토큰 get
        String kakaoToken = kakaoService.getReturnAccessToken(code);
        // 접속자 정보 get
        Map<String, Object> result = kakaoService.getUserInfo(kakaoToken);
        System.out.print("result:: " + result);

        if (result == null || result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        String snsId = (String) result.get("id");
        String username = (String) result.get("email");
        boolean isRegistered = snsIdCheckService.checkAndRegisterUser(Long.parseLong(snsId));
        System.out.println("is registered in : " +  isRegistered);

        if (isRegistered) {
            // 가입된 유저라면 로그인 처리
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(username, snsId);
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = userService.getUserByUsername(username);
            // JWT 토큰 생성
            String jwt = tokenProvider.createToken( authentication, user.getId(), user.getIsTutor(), user.getNickname());
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(jwt);

        }
        return ResponseEntity.ok(result);

    }

//    @GetMapping("kakao_callback")
//    public ResponseEntity<Object> redirectkakao(@RequestParam String code, HttpSession session) throws IOException {
//        System.out.println("code:: " + code);
//
//        // 접속토큰 get
//        String kakaoToken = kakaoService.getReturnAccessToken(code);
//        System.out.print(kakaoToken);
//        // 접속자 정보 get
//        Map<String, Object> result = kakaoService.getUserInfo(kakaoToken);
//        log.info("result:: " + result);
//
//        String snsId = (String) result.get("id");
//        String email = (String) result.get("email");
//        UserSns userSns = userSnsService.findBySnsId(Long.parseLong(snsId));
//        if (userSns == null) {
//            ObjectMapper objectMapper = new ObjectMapper();
//            String jsonResponse = objectMapper.writeValueAsString(Map.of("snsId", snsId, "email", email));
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(jsonResponse);
//        }
//
//        User user = userService.findById(userSns.getId());
//
//        // Assuming you have user and loginDto ready for authentication
//        LoginDto loginDto = new LoginDto();
//        loginDto.setUsername(email);
//        loginDto.setPassword(snsId);
//
//        ResponseEntity<TokenDto> responseEntity = restTemplate.postForEntity(
//                baseUrl + "/api/authenticate", // Your authenticate endpoint URL
//                loginDto, // LoginDto object
//                TokenDto.class // Expected response type
//        );
//
//        /* 로그아웃 처리 시, 사용할 토큰 값 */
//        session.setAttribute("kakaoToken", kakaoToken);
//
//        return ResponseEntity.ok()
//                .headers(responseEntity.getHeaders())
//                .body(responseEntity.getBody());
//    }

    @PostMapping("/kakao/signup")
    public ResponseEntity<TokenDto> signUpAndAuthorizeWithKakao(@RequestBody SnsSignUpReqDto snsSignUpReqDto) throws Exception {
        // SNS 회원가입

        SignUpReqDto signUpReqDto = snsSignUpReqDto.toSignUpReqDto();
        if(signUpReqDto.getIsTutor()) {
            SignUpReqDto registeredUserDto = userService.signupTutor(signUpReqDto, true); // 저장된 User 정보가 담긴 DTO 반환
            userSnsService.snsSignup(registeredUserDto.getId(), "kakao", snsSignUpReqDto.getSnsId());
        }
        else {
            SignUpReqDto registeredUserDto = userService.signupStudent(signUpReqDto, true); // 저장된 User 정보가 담긴 DTO 반환
            userSnsService.snsSignup(registeredUserDto.getId(), "kakao", snsSignUpReqDto.getSnsId());
        }
        // 로그인 처리
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(signUpReqDto.getUsername(), snsSignUpReqDto.getSnsId());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // JWT 토큰 생성
        String jwt = tokenProvider.createToken( authentication, signUpReqDto.getId(), signUpReqDto.getIsTutor(), signUpReqDto.getNickname()); // false를 주어 isAdmin을 처리
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
    }

}