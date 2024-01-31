//package com.example.career.global.Exception;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//
//    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<?> handleException(Exception e) {
//        // 예외를 ERROR 레벨로 로그에 기록합니다.
//        System.out.println(e);
//        logger.error("An error occurred: ", e);
//
//        // 클라이언트에게 반환할 에러 응답을 생성합니다.
//        ErrorResponse errorResponse = new ErrorResponse("Internal Server Error : " + e, HttpStatus.INTERNAL_SERVER_ERROR.value());
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
//    }
//    // ErrorResponse 클래스는 클라이언트에게 반환할 에러 정보를 포함합니다.
//    @Data
//    @AllArgsConstructor
//    @NoArgsConstructor
//    public static class ErrorResponse {
//        private String message;
//        private int status;
//
//    }
//}
