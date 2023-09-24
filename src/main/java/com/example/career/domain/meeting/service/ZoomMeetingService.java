package com.example.career.domain.meeting.service;

import com.example.career.domain.meeting.dto.ZoomMeetingObjectDTO;
import com.example.career.domain.meeting.entity.ZoomMeetingObjectEntity;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

public interface ZoomMeetingService {
    public ZoomMeetingObjectEntity createMeeting(ZoomMeetingObjectDTO zoomMeetingObjectDTO) throws IOException;
    public String refreshToken() throws IOException;
}
