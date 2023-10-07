package com.example.career.global.aop;

import com.example.career.domain.user.Entity.User;
import com.example.career.domain.user.Service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.security.Key;

@Aspect
@Component
@RequiredArgsConstructor
public class AuthenticationAspect {
    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Value("${jwt.secret}")
    private String secret;

    private final UserService userService;

    @Before("@annotation(com.example.career.global.annotation.Authenticated)")
    public void authenticate(JoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sra.getRequest();

        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        String token = "";
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            token = bearerToken.substring(7);
        }

        byte[] keyBytes = Decoders.BASE64.decode(secret);
        Key key = Keys.hmacShaKeyFor(keyBytes);

        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        String subject = claims.getSubject();

        User user = userService.getUserByUsername(subject);
        request.setAttribute("userId", user.getId());
        request.setAttribute("nickname", user.getNickname());
        request.setAttribute("isTutor", user.getIsTutor());
        // request에 attribute로 subject를 추가
        request.setAttribute("subject", subject);
    }
}