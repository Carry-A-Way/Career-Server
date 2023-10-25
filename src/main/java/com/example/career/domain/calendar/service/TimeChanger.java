package com.example.career.domain.calendar.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.BitSet;

// TutorSlot 에 들어갈 시간을 Long으로 바꿔주거나 그 반대의 메서드
public class TimeChanger {
    public static byte[] dateTimeToByte(LocalDateTime start, LocalDateTime end) {
        BitSet bits = new BitSet(48);
        LocalDateTime current = start;

        // 시작시간 이후 계산
        while (current.isBefore(end)) {
            int hour = current.getHour();
            int minute = current.getMinute();
            int index = hour * 2 + (minute / 30);
            bits.set(index);

            current = current.plus(30, ChronoUnit.MINUTES);
        }
        return bitSetToByte(bits);
    }
    public static byte[] bitSetToByte(BitSet bitSet) {
        int numBytes = (bitSet.length() + 7) / 8; // BitSet의 비트 수에 따른 바이트 배열 크기 계산
        byte[] byteArray = new byte[numBytes];

        for (int i = 0; i < bitSet.length(); i++) {
            if (bitSet.get(i)) {
                int byteIndex = i / 8;
                int bitIndex = 7 - (i % 8);
                byteArray[byteIndex] |= (1 << bitIndex);
            }
        }

        return byteArray;
    }

}
