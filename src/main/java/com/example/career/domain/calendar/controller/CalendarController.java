package com.example.career.domain.calendar.controller;

import com.example.career.domain.calendar.dto.CalendarMentorRespDto;
import com.example.career.domain.calendar.service.CalendarService;
import com.example.career.domain.consult.Dto.CalendarDenyReqDto;
import com.example.career.domain.consult.Entity.Consult;
import com.example.career.global.annotation.Authenticated;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("calendar")
public class CalendarController {
    private final CalendarService calendarService;

    @GetMapping("mentor/view")
    public ResponseEntity<CalendarMentorRespDto> getCalendarByMentorId(@RequestParam Long mentorId) {

        return new ResponseEntity<>(calendarService.getMentorCalendar(mentorId), HttpStatus.OK);
    }
    @Authenticated
    @PostMapping("mentor/deny")
    public ResponseEntity<Consult> DenyConsultByMentor(@RequestBody CalendarDenyReqDto calendarDenyReqDto, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        int validCheck = userId.compareTo(calendarDenyReqDto.getId()); // 같으면 0 틀리면 1
        Consult consult = calendarService.denyConsultByMentor(calendarDenyReqDto);

        // JWT의 ID와 Parameter ID 값이 같은지 확인
        if(validCheck == 0 && consult != null) {
            return new ResponseEntity<>(consult, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

    @Authenticated
    @PostMapping("mentor/accept")
    public ResponseEntity<Consult> AcceptConsultByMentor(@RequestBody CalendarDenyReqDto calendarDenyReqDto, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        int validCheck = userId.compareTo(calendarDenyReqDto.getId()); // 같으면 0 틀리면 1
        Consult consult = calendarService.AcceptConsultByMentor(calendarDenyReqDto);

        // JWT의 ID와 Parameter ID 값이 같은지 확인
        if(validCheck == 0 && consult != null) {
            return new ResponseEntity<>(consult, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

}
