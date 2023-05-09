package com.example.career.Controller;

import com.example.career.domain.user.Dto.UserReqDto;
import com.example.career.global.security.JwtFilter;
import com.example.career.global.security.OperatorDTO;
import com.example.career.global.security.TokenDTO;
import com.example.career.global.security.TokenProvider;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping("/authenticate")
    public ResponseEntity<TokenDTO> authorize(@Valid @RequestBody UserReqDto userReqDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userReqDto.getUsername(), userReqDto.getPassword());
//        authenticationToken.setAuthenticated(true);
        System.out.println(authenticationToken);
        //authenticationManagerBuilder가 호출되면서 CustomUserDetailService가 로드됨.
        System.out.println("00000");
        try {
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

            System.out.println("111111");
            System.out.println(authentication);
            System.out.println("222222222");
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = tokenProvider.createToken(authentication);
            System.out.println(jwt);
            System.out.println("3333333333");

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

            return new ResponseEntity<>(new TokenDTO(jwt), httpHeaders, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}