package com.example.career.Controller;

import com.example.career.domain.consult.Service.ConsultService;
import com.example.career.global.annotation.Authenticated;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import static com.example.career.global.jwt.JwtFilter.AUTHORIZATION_HEADER;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Key;

@RestController
@RequiredArgsConstructor

public class HomeController {
    private final ConsultService consultService;
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    // 상담 시간
    @PostMapping("schedule/update/consultation")
    public ResponseEntity<?> updateConsultationStatus() {
        return consultService.updateConsultationStatus();
    }

    @PostMapping("test")
    public TestDTO conntectTest() {
        return new TestDTO();
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

//    @Authenticated
//    @GetMapping("/test/jwt")
//    public String jwtTest(HttpServletRequest request) {
//        String subject = (String) request.getAttribute("subject");
//        return subject;
//    }

}
@Data
class TestDTO {
    private int code = 400;
}
