package com.example.career.domain.calendar.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimeValidCheck {
        public static boolean isAfterCurrentTime(LocalDateTime start, LocalDateTime end) {
            // 서울 시간대로 설정
            ZoneId seoulZoneId = ZoneId.of("Asia/Seoul");
            ZonedDateTime seoulCurrentTime = ZonedDateTime.now(seoulZoneId);
            // 현재 시간(서울)과 비교, start와 end 비교
            return start.isAfter(seoulCurrentTime.toLocalDateTime())        // start와 현재 시간
                    && end.isAfter(seoulCurrentTime.toLocalDateTime() )     // end와 현재 시간
                    && end.isAfter(start);                                  // start와 end 현재 시간
        }
}
