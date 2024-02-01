package com.example.career.global.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    private final ObjectMapper objectMapper;

    @Pointcut("within(*..*Controller)")
    public void onRequest() {}

    @Around("onRequest()")
    public Object requestLogging(ProceedingJoinPoint joinPoint) throws Throwable {
        // HttpServletRequest를 얻습니다.
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        // 클라이언트 IP와 사용자 에이전트를 추출합니다.
        String clientIpAddress = request.getHeader("X-Forwarded-For");
        if (clientIpAddress == null) {
            clientIpAddress = request.getRemoteAddr();
        }
        String userAgent = request.getHeader("User-Agent");

        // 인증 정보를 얻습니다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            username = userDetails.getUsername();
        }

        // 로그 정보를 생성합니다.
        final RequestApiInfo apiInfo = new RequestApiInfo(joinPoint, joinPoint.getTarget().getClass(), objectMapper);

        Object[] parameters = apiInfo.getParameters();
        String parametersAsString = Arrays.stream(parameters)
                .map(p -> p instanceof HttpServletRequest ? "HttpServletRequest" : p.toString())
                .collect(Collectors.joining(", ", "[", "]"));

        final LogInfo logInfo = new LogInfo(
                apiInfo.getUrl(),
                apiInfo.getName(),
                apiInfo.getMethod(),
                parametersAsString,
                objectMapper.writeValueAsString(apiInfo.getBody()),
                clientIpAddress, // 클라이언트 IP 주소
                username, // 유저 이름
                userAgent // 사용자 에이전트
        );

        try {
            final Object result = joinPoint.proceed(joinPoint.getArgs());
            final String logMessage = objectMapper.writeValueAsString(Map.entry("logInfo", logInfo));
            logger.info(logMessage);
            return result;
        } catch (Exception e) {
            final StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            final String exceptionAsString = sw.toString();
            logInfo.setException(exceptionAsString);
            final String logMessage = objectMapper.writeValueAsString(logInfo);
            logger.error(logMessage);
            throw e;
        }
    }
}
