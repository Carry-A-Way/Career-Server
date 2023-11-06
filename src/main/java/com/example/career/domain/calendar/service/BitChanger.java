package com.example.career.domain.calendar.service;

import com.example.career.domain.calendar.dto.PossibleTime;
import com.example.career.domain.calendar.dto.PossibleTimeSortByDate;
import com.example.career.domain.consult.Entity.TutorSlot;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class BitChanger {
    public static PossibleTimeSortByDate convertByteArrayToTimeList(TutorSlot tutorSlot) {
        byte[] byteArray = tutorSlot.getPossibleTime();
        LocalDate startDate = tutorSlot.getConsultDate();

        PossibleTimeSortByDate date = new PossibleTimeSortByDate();
        date.setDate(startDate);

        List<PossibleTime> possibleTimeList = new ArrayList();
        PossibleTime possibleTime = null;
        int hour = 1;
        int minute = 0;
        boolean check = false;
        int count = 0;

        for (byte b : byteArray) {
            for (int i = 7; i >= 0; i--) {
                int bit = (b >> i) & 1;
                if (bit == 1) {
                    if (count % 2 == 0) {
                        minute = 0;
                    } else {
                        minute = 30;
                    }
                    if (!check) {
                        possibleTime = new PossibleTime(); // 새 객체 생성
                        possibleTime.setStart(hour * count / 2 + ":" + minute);

                    }
                    // 24:00시 일때
                    if(count == 47) {
                        int temphour = hour * (count+1) / 2;
                        minute = 0;
                        System.out.println(count+ " 시간: "+ temphour + ":" +minute);
                        possibleTime.setEnd(temphour + ":" + minute);
                        possibleTimeList.add(possibleTime);
                    }

                    check = true;
                } else {
                    if (check) {
                        int temphour = hour * count / 2;

                        if (count % 2 == 0) {
                            minute = 0;
                        } else {
                            minute = 30;
                        }
                        System.out.println(count+ " 시간: "+ temphour + ":" +minute);
                        possibleTime.setEnd(temphour + ":" + minute);
                        possibleTimeList.add(possibleTime);
                    }
                    check = false;
                }
                count++;
            }
        }

        date.setPossibleTimeList(possibleTimeList);
        return date;
    }


}


