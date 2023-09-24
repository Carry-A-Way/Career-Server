package com.example.career.domain.meeting.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ZoomTokenController {

    @Value("${zoom.client-id}")
    private String clientID;

    @GetMapping("/zoom/auth")
    public String redirectToZoomAuth() {
        // Zoom OAuth 2.0 인증 시작 URL
        return "redirect:https://zoom.us/oauth/authorize?response_type=code&client_id="+clientID+"&redirect_uri=http://localhost:8080/_new/support/reservation/zoomApi";
    }
}
