package com.example.career.domain.oauth.Controller;

import com.example.career.domain.oauth.Dto.SnsIdCheckReqDto;
import com.example.career.domain.oauth.Service.SnsIdCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth/kakao")
public class SnsIdCheckController {
    @Autowired
    private SnsIdCheckService userSnsService;

    @PostMapping("/check-register")
    public ResponseEntity<String> loginOrRegister(@RequestBody SnsIdCheckReqDto snsIdCheckReqDto) {
        Long snsId = snsIdCheckReqDto.getSnsId();
        boolean isLoggedIn = userSnsService.checkAndRegisterUser(snsId);
        if (isLoggedIn) {
            return ResponseEntity.ok("true");
        } else {
            return ResponseEntity.ok("false");
        }
    }
}