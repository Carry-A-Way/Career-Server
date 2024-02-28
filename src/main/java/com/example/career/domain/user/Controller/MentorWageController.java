package com.example.career.domain.user.Controller;

import com.example.career.domain.user.Dto.WageReqDto;
import com.example.career.domain.user.Entity.User;
import com.example.career.domain.user.Service.MentorWageService;
import com.example.career.global.annotation.Authenticated;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("mentor/wage")
@RequiredArgsConstructor
public class MentorWageController {

    private final MentorWageService mentorWageService;
    @Authenticated
    @PostMapping("update")
    public ResponseEntity<Object> updateMentorWage(@RequestBody WageReqDto wageReqDto, HttpServletRequest request) {
        User userAop = (User) request.getAttribute("user");
        Long id = userAop.getId();
        mentorWageService.updateMentorWage(wageReqDto, id);

        return ResponseEntity.ok(wageReqDto);
    }
}
