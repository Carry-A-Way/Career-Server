package com.example.career.domain.calendar.service;

import com.example.career.domain.calendar.dto.CalendarMentorRespDto;
import com.example.career.domain.consult.Dto.CalendarDenyReqDto;
import com.example.career.domain.consult.Entity.Consult;

import java.io.IOException;

public interface CalendarService {
    public CalendarMentorRespDto getMentorCalendar(Long id);
    public Consult denyConsultByMentor(CalendarDenyReqDto calendarDenyReqDto);
    public Consult AcceptConsultByMentor(CalendarDenyReqDto calendarDenyReqDto, String username) throws IOException;
}
