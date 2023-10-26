package com.example.career.domain.consult.Controller;

import com.example.career.domain.consult.Dto.ConsultRespDto;
import com.example.career.domain.consult.Dto.ConsultYesorNoReqDto;
import com.example.career.domain.consult.Entity.Consult;
import com.example.career.domain.consult.Repository.ConsultRepository;
import com.example.career.domain.consult.Service.ConsultService;
import com.example.career.domain.user.Entity.User;
import com.example.career.global.annotation.Authenticated;
import com.example.career.global.valid.ValidCheck;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("consultation")
public class ConsultController {
    private final ConsultService consultService;
    @Authenticated
    @GetMapping ("list-by-status")
    public ValidCheck consultUpcoming(@RequestParam int status, HttpServletRequest request){
        User user = (User) request.getAttribute("user");
        return new ValidCheck(consultService.getList(user, status));
    }
    @PatchMapping("request")
    public ValidCheck consultAcception(@RequestParam int status, @RequestBody ConsultYesorNoReqDto consultYesorNoReqDto){
        return new ValidCheck(consultService.requestConsult(consultYesorNoReqDto, status));
    }

    // 멘토 홈페이지 상담내역 메서드
    @Authenticated
    @GetMapping ("mentor")
    public ValidCheck mentorHome(HttpServletRequest request){
        User user = (User) request.getAttribute("user");
        return new ValidCheck(consultService.getMentorHome(user));
    }
}
