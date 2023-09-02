package com.example.career.Controller;
import com.example.career.domain.user.Dto.LoginDto;
import com.example.career.domain.user.Dto.TokenDto;
import com.example.career.global.jwt.JwtFilter;
import com.example.career.global.jwt.TokenProvider;
import com.example.career.global.valid.ValidCheck;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class AuthController {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public AuthController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<ValidCheck> authorize(@Valid @RequestBody LoginDto loginDto, HttpServletResponse response) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwtAccessToken = tokenProvider.createAccessToken(authentication);
        String jwtRefreshToken = tokenProvider.createRefreshToken(authentication); // 가정: 이러한 메소드가 있습니다.

        response.setHeader("Set-Cookie", "refreshToken=" + jwtRefreshToken + "; SameSite=None; Secure");

        Cookie cookie = new Cookie("useremail","blueskii");
        cookie.setPath("/");
        // 30초간 저장
        cookie.setMaxAge(30*60);
        cookie.setSecure(true);
        response.addCookie(cookie);

        TokenDto tokenDto = new TokenDto(jwtAccessToken); // 응답에는 accessToken만 포함

        return new ResponseEntity<>(new ValidCheck(tokenDto), HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        String refreshToken = tokenProvider.extractRefreshTokenFromCookie(request);
        if (refreshToken == null || !tokenProvider.validateRefreshToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
        }

        String newAccessToken = tokenProvider.createAccessTokenFromRefreshToken(refreshToken);
        return ResponseEntity.ok(new TokenDto(newAccessToken));
    }

}