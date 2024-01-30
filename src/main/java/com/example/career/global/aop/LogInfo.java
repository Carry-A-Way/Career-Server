package com.example.career.global.aop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogInfo {
    private String url;
    private String name;
    private String method;
//    private String header;
    private String parameters;
    private String body;
    private String ipAddress;
//    private String userId;
    private String username;
    private String exception; // 예외 정보

    public LogInfo(String url, String name, String method, String parameters, String body, String ipAddress, String userName) {
        this.url = url;
        this.name = name;
        this.method = method;
//        this.header = header;
        this.parameters = parameters;
        this.body = body;
        this.ipAddress = ipAddress;
//        this.userId = userId;
        this.username = userName;
    }

}