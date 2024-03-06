package com.example.career.domain.consult.Controller;

import com.example.career.domain.community.Dto.Brief.UserBriefWithRate;
import com.example.career.domain.consult.Dto.*;
import com.example.career.domain.consult.Entity.Consult;
import com.example.career.domain.consult.Entity.Review;
import com.example.career.domain.consult.Repository.ConsultRepository;
import com.example.career.domain.consult.Service.ConsultService;
import com.example.career.domain.user.Entity.User;
import com.example.career.global.annotation.Authenticated;
import com.example.career.global.valid.ValidCheck;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    // 멘토, 멘티 홈페이지 상담내역 메서드 (전체 조회)
    @Authenticated
    @GetMapping ("user")
    public ValidCheck mentorHome(HttpServletRequest request){
        User user = (User) request.getAttribute("user");
        return new ValidCheck(consultService.getMentorHome(user));
    }


    // 멘토가 상담에 참여
    @GetMapping ("mentor/join")
    public void mentorJoinInConsult(@RequestBody ConsultJoinReqDto consultJoinReqDto){
        consultService.mentorJoinInConsult(consultJoinReqDto.getConsultId());
    }
    @Authenticated
    @PostMapping("write/review")
    public ReviewRespDto writeReviewAfterConsult(HttpServletRequest request, @RequestBody ReviewWriteReqDto reviewWriteReqDto) {
        User user = (User) request.getAttribute("user");
        return consultService.writeReview(user, reviewWriteReqDto);
    }

    @Authenticated
    @PostMapping("update/review")
    public ReviewRespDto updateReviewAfterConsult(HttpServletRequest request, @RequestBody ReviewWriteReqDto reviewWriteReqDto) {
        User user = (User) request.getAttribute("user");
        return consultService.updateReview(user, reviewWriteReqDto);
    }

    @Authenticated
    @GetMapping("scheduled")
    public ResponseEntity<?> menteeScheduledConsultList(HttpServletRequest request) {
        User user = (User) request.getAttribute("user");

        if(user.getIsTutor()) return ResponseEntity.badRequest().body("멘티가 아닙니다. 다시.");
        return consultService.menteeScheduledConsultList(user);
    }

}
