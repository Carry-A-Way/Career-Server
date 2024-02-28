package com.example.career.domain.user.Service;

import com.example.career.domain.user.Dto.WageReqDto;
import org.springframework.web.bind.annotation.RequestBody;

public interface MentorWageService {
    void updateMentorWage(WageReqDto wageReqDto, Long id);
}
