package com.example.career.domain.calendar.controller;

import com.example.career.domain.calendar.dto.CalendarGetPossibleTimeRespDto;
import com.example.career.domain.calendar.dto.CalendarMentorPossibleReqDto;
import com.example.career.domain.calendar.dto.CalendarMentorRespDto;
import com.example.career.domain.calendar.dto.CalendarRegistReqDto;
import com.example.career.domain.calendar.service.CalendarService;
import com.example.career.domain.calendar.service.TimeValidCheck;
import com.example.career.domain.consult.Dto.CalendarDenyReqDto;
import com.example.career.domain.consult.Entity.Consult;
import com.example.career.domain.consult.Entity.TutorSlot;
import com.example.career.domain.user.Entity.User;
import com.example.career.global.annotation.Authenticated;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("calendar")
public class CalendarController {
    private final CalendarService calendarService;
    @Authenticated
    @GetMapping("mentor/view")
    public ResponseEntity<CalendarMentorRespDto> getCalendarByMentorId(HttpServletRequest request, @RequestParam Long mentorId) {

        return new ResponseEntity<>(calendarService.getMentorCalendar(mentorId), HttpStatus.OK);
    }

    // 상담 거절 - 멘토가
    @Authenticated
    @PostMapping("mentor/deny")
    public ResponseEntity<Boolean> DenyConsultByMentor(@RequestBody CalendarDenyReqDto calendarDenyReqDto, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        return new ResponseEntity<>(calendarService.denyConsultByMentor(user, calendarDenyReqDto), HttpStatus.OK);

    }
    // 상담 수락 - 멘토가
    @Authenticated
    @PostMapping("mentor/accept")
    public ResponseEntity<Boolean> AcceptConsultByMentor(@RequestBody CalendarDenyReqDto calendarDenyReqDto, HttpServletRequest request) throws IOException {

        User user = (User) request.getAttribute("user");
        return new ResponseEntity<>(calendarService.AcceptConsultByMentor(calendarDenyReqDto, user), HttpStatus.OK);

    }

    // 상담 신청 - 멘티가
    @PostMapping("mentee/register")
    public ResponseEntity<String> RegisterConsultByMentee(@RequestBody CalendarRegistReqDto calendarRegistReqDto) {

        return calendarService.RegisterConsultByMentee(calendarRegistReqDto);
    }

    // 멘토 캘린더 상담 가능날짜 추가
    @Authenticated
    @PostMapping("mentor/insert/possible/time")
    public ResponseEntity<Boolean> insertMentorPossibleTime(@RequestBody CalendarMentorPossibleReqDto calendarMentorPossibleReqDto, HttpServletRequest request) {
        if (!TimeValidCheck.isAfterCurrentTime(calendarMentorPossibleReqDto.getStart(), calendarMentorPossibleReqDto.getEnd())) return new ResponseEntity<>(false,HttpStatus.OK);
        User user = (User) request.getAttribute("user");
        Long userId = user.getId();
        System.out.println(calendarMentorPossibleReqDto);
        return new ResponseEntity<>(calendarService.insertMentorPossibleTime(calendarMentorPossibleReqDto, userId),HttpStatus.OK);
    }
    // 상담 가능 시간대 조회
    @Authenticated
    @PostMapping("mentor/get/possible/time")
    public ResponseEntity<CalendarGetPossibleTimeRespDto> getMentorPossibleTime(HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        Long userId = user.getId();
        return new ResponseEntity<>(calendarService.getMentorPossibleTime(userId),HttpStatus.OK);
    }
    // 멘티가 멘토의 상담 가능 시간대 조회
    @PostMapping("mentee/get/possible/time")
    public ResponseEntity<CalendarGetPossibleTimeRespDto> menteeGetMentorsPossibleTime( @RequestParam Long mentorId) {
        return new ResponseEntity<>(calendarService.getMentorPossibleTime(mentorId),HttpStatus.OK);
    }
    // 상담 가능 시간대 삭제
    @Authenticated
    @PostMapping("mentor/delete/possible/time")
    public ResponseEntity<Boolean> deleteMentorPossibleTime(@RequestBody CalendarMentorPossibleReqDto calendarMentorPossibleReqDto,HttpServletRequest request) {
        if (!TimeValidCheck.isAfterCurrentTime(calendarMentorPossibleReqDto.getStart(), calendarMentorPossibleReqDto.getEnd())) return new ResponseEntity<>(false,HttpStatus.valueOf("날짜가 올바르지 않습니다."));
        User user = (User) request.getAttribute("user");
        Long userId = user.getId();
        return new ResponseEntity<>(calendarService.deleteMentorPossibleTime(calendarMentorPossibleReqDto, userId),HttpStatus.OK);
    }


}
