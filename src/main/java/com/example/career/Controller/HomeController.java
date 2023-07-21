package com.example.career.Controller;

import com.example.career.domain.user.Entity.User;
import com.example.career.domain.user.Repository.UserRepository;
import com.example.career.global.annotation.Authenticated;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Key;
import java.util.List;

import static com.example.career.global.jwt.JwtFilter.AUTHORIZATION_HEADER;

@RestController
@AllArgsConstructor

public class HomeController {

    final UserRepository careerRepository;

    @GetMapping("/test")
    public List<User> test(){
        return careerRepository.findAll();
    }
    @PostMapping("/test/insert")
    public String testInsert(){
        return "성공";
    }
    @GetMapping("/test/test")
    public String gogo(ServletRequest servletRequest, @Value("${jwt.secret}") String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        Key key = Keys.hmacShaKeyFor(keyBytes);
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String bearerToken = httpServletRequest.getHeader(AUTHORIZATION_HEADER);

        String token = "";
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            token = bearerToken.substring(7);
        }

        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        String subject = claims.getSubject();
        return subject;
    }


    @Authenticated
    @GetMapping("/test/jwt")
    public String jwtTest(HttpServletRequest request) {
        String subject = (String) request.getAttribute("subject");
        return subject;
    }

    @GetMapping("/api/hello")
    public String test2() {
        return "Hello, world!";
    }
}
