package com.example.career.domain.calendar.service;

import com.example.career.domain.calendar.dto.CalendarGetPossibleTimeRespDto;
import com.example.career.domain.calendar.dto.CalendarMentorPossibleReqDto;
import com.example.career.domain.calendar.dto.CalendarMentorRespDto;
import com.example.career.domain.calendar.dto.CalendarRegistReqDto;
import com.example.career.domain.consult.Dto.CalendarDenyReqDto;
import com.example.career.domain.consult.Entity.Consult;
import com.example.career.domain.consult.Entity.TutorSlot;

import java.io.IOException;
import java.util.List;

public interface CalendarService {
    public CalendarMentorRespDto getMentorCalendar(Long id);
    public Consult denyConsultByMentor(CalendarDenyReqDto calendarDenyReqDto);
    public Consult AcceptConsultByMentor(CalendarDenyReqDto calendarDenyReqDto, String username) throws IOException;
    public Consult RegisterConsultByMentee(CalendarRegistReqDto calendarRegistReqDto);
    public TutorSlot insertMentorPossibleTime(CalendarMentorPossibleReqDto calendarMentorPossibleReqDto, Long userId);
    public CalendarGetPossibleTimeRespDto getMentorPossibleTime(Long userId);
}
