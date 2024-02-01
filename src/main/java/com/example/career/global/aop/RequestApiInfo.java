package com.example.career.global.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestApiInfo {
    private String url;
    private String method;
    private Object[] parameters;
    private String ipAddress;
    private String userId;
    private String userName;
    // 추가된 필드
    private String name;
    private Map<String, String> header;
    private Object body;

    public RequestApiInfo(ProceedingJoinPoint joinPoint, Class<?> targetClass, ObjectMapper objectMapper) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        this.url = request.getRequestURI();
        this.method = request.getMethod(); // HTTP 메서드를 얻습니다.
        this.parameters = joinPoint.getArgs();
        this.ipAddress = request.getRemoteAddr();

        // 여기에 name, header, body 초기화 로직을 추가합니다.
        this.name = targetClass.getSimpleName(); // 클래스 이름으로 예시 구현
//        this.header = "header_test";
//        this.body = /* 요청 바디 추출 로직 */;
    }
}
