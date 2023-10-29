package com.example.career.global.time;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class KoreaTime {
    private static final ZoneId KOREA_ZONE_ID = ZoneId.of("Asia/Seoul");

    public static LocalDateTime now() {
        ZonedDateTime zdt = ZonedDateTime.now(KOREA_ZONE_ID);
        return zdt.toLocalDateTime();
    }
}
