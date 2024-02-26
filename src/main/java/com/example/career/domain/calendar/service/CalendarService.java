package com.example.career.domain.calendar.service;

import com.example.career.domain.calendar.dto.CalendarGetPossibleTimeRespDto;
import com.example.career.domain.calendar.dto.CalendarMentorPossibleReqDto;
import com.example.career.domain.calendar.dto.CalendarMentorRespDto;
import com.example.career.domain.calendar.dto.CalendarRegistReqDto;
import com.example.career.domain.consult.Dto.CalendarDenyReqDto;
import com.example.career.domain.consult.Entity.Consult;
import com.example.career.domain.consult.Entity.TutorSlot;
import com.example.career.domain.user.Entity.User;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

public interface CalendarService {
    public CalendarMentorRespDto getMentorCalendar(Long mentorId);
    public Boolean denyConsultByMentor(User user, CalendarDenyReqDto calendarDenyReqDto);
    public Boolean AcceptConsultByMentor(CalendarDenyReqDto calendarDenyReqDto, User user) throws IOException;
    public ResponseEntity<String> RegisterConsultByMentee(CalendarRegistReqDto calendarRegistReqDto);
    public boolean insertMentorPossibleTime(CalendarMentorPossibleReqDto calendarMentorPossibleReqDto, Long userId);
    public CalendarGetPossibleTimeRespDto getMentorPossibleTime(Long userId);
    public boolean deleteMentorPossibleTime(CalendarMentorPossibleReqDto calendarMentorPossibleReqDto,Long userId);
}
