package com.example.career.domain.oauth.Controller;

import com.example.career.domain.oauth.Dto.SnsSignUpReqDto;
import com.example.career.domain.oauth.Entity.UserSns;
import com.example.career.domain.oauth.Service.KakaoService;
import com.example.career.domain.oauth.Service.UserSnsService;
import com.example.career.domain.user.Dto.LoginDto;
import com.example.career.domain.user.Dto.SignUpReqDto;
import com.example.career.domain.user.Dto.TokenDto;
import com.example.career.domain.user.Entity.User;
import com.example.career.domain.user.Service.UserService;
import com.example.career.global.jwt.JwtFilter;
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
    private final UserSnsService userSnsService;
    private final UserService userService;

    @Value("${api.base-url}")
    private String baseUrl;

//    private final MemberService memberService;

    @Transactional
    @PostMapping("kakao_signup")
    public ResponseEntity<Object> signUpWithKakao(@RequestBody SnsSignUpReqDto snsSignUpReqDto) {
        SignUpReqDto signUpReqDto = snsSignUpReqDto.toSignUpReqDto();
        SignUpReqDto res = null;

        if (snsSignUpReqDto.getIsTutor() == true) { // 튜터의 경우
            res = userService.signupTutor(signUpReqDto, true);
        } else { // 튜티의 경우
            res = userService.signupStudent(signUpReqDto, true);
        }
        userSnsService.snsSignup(res.getId(), 0, snsSignUpReqDto.getSnsId());

        return ResponseEntity.ok(snsSignUpReqDto);
    }

    @GetMapping("kakao_callback")
    public ResponseEntity<Object> redirectkakao(@RequestParam String code, HttpSession session) throws IOException {
        System.out.println("code:: " + code);

        // 접속토큰 get
        String kakaoToken = kakaoService.getReturnAccessToken(code);

        // 접속자 정보 get
        Map<String, Object> result = kakaoService.getUserInfo(kakaoToken);
        log.info("result:: " + result);

        String snsId = (String) result.get("id");
        String email = (String) result.get("email");
        UserSns userSns = userSnsService.findBySnsId(Long.parseLong(snsId));
        if (userSns == null) {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(Map.of("snsId", snsId, "email", email));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(jsonResponse);
        }

        User user = userService.findById(userSns.getId());

        // Assuming you have user and loginDto ready for authentication
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername(user.getUsername());
        loginDto.setPassword(snsId);

        ResponseEntity<TokenDto> responseEntity = restTemplate.postForEntity(
                baseUrl + "/api/authenticate", // Your authenticate endpoint URL
                loginDto, // LoginDto object
                TokenDto.class // Expected response type
        );

        /* 로그아웃 처리 시, 사용할 토큰 값 */
        session.setAttribute("kakaoToken", kakaoToken);

        return ResponseEntity.ok()
                .headers(responseEntity.getHeaders())
                .body(responseEntity.getBody());
    }
}