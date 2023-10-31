package com.example.career.domain.calendar.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

// TutorSlot 에 들어갈 시간을 Long으로 바꿔주거나 그 반대의 메서드
public class TimeChanger {
    public byte[] dateTimeToByte(LocalDateTime start, LocalDateTime end) {
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
    public byte[] bitSetToByte(BitSet bitSet) {
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
    // newByte oldByte 합체
    /***
    * newByte : 추가 또는 제거를 원하는 시간대
    * oldByte : 현재 멘토가 가지고 있는 시간대
    * useAND : true( ~new AND old, DELETE )
    *          false(new OR old, INSERT )
    * */
    public byte[] combineBytesWithXOR(byte[] newByte, byte[] oldByte, boolean isDelete) {
        int maxLength = Math.max(newByte.length, oldByte.length);
        newByte = padByteArray(newByte, maxLength);
        oldByte = padByteArray(oldByte, maxLength);

        byte[] combinedByte = new byte[maxLength];

        for (int i = 0; i < maxLength; i++) {
            if (isDelete) {
                combinedByte[i] = (byte) (~newByte[i] & oldByte[i]); // Using AND operation : Delete
            } else {
                combinedByte[i] = (byte) (newByte[i] | oldByte[i]); // Using OR operation : Insert
            }
        }
        System.out.println("old");
        printByte(oldByte);
        System.out.println("new");
        printByte(newByte);
        System.out.println("now");
        printByte(combinedByte);

        return combinedByte;
    }
    public byte[] padByteArray(byte[] byteArray, int length) {
        if (byteArray.length >= length) {
            return byteArray;
        }
        byte[] paddedArray = new byte[length];
        System.arraycopy(byteArray, 0, paddedArray, 0, byteArray.length);
        return paddedArray;
    }


    public int countOnes(byte[] byteArray) {
        int count = 0;
        for (byte b : byteArray) {
            for (int i = 0; i < 8; i++) {
                if ((b & (1 << i)) != 0) {
                    count++;
                }
            }
        }
        return count;
    }
    public void printByte(byte[] combinedByte) {
        if (combinedByte != null) {
            System.out.print("Combined Bytes: ");
            for (byte b : combinedByte) {
                System.out.print(Integer.toBinaryString(b & 255 | 256).substring(1) + " ");
            }
            System.out.println();
        } else {
            System.out.println("Combined Bytes: null");
        }
    }

    // 상담 시간 확인
    public boolean checkIndexesInOldForOnesInNew(byte[] oldByte, byte[] newByte) {
        List<Integer> indexes = calculateIndexesWithOnes(newByte);
        System.out.println(indexes);
        // Check if all indexes in old have corresponding 1s in new
        for (int index : indexes) {
            if (index >= oldByte.length * 8) {
                return false; // Return false if any index is out of range
            }

            int byteIndex = index / 8; // Calculate the index of the byte in the array
            int bitIndex = index % 8; // Calculate the index of the bit within the byte

            // Extract the specific bits from the bytes
            byte oldByteValue = oldByte[byteIndex];

            // Extract the bit at the specified index from the byte
            int oldBit = (oldByteValue >> (7 - bitIndex)) & 1;

            if (oldBit != 1) {
                return false; // Return false if any index does not have a corresponding 1 in old
            }
        }
        return true; // If all indexes in old have corresponding 1s in new
    }

    public List<Integer> calculateIndexesWithOnes(byte[] newByte) {
        List<Integer> indexes = new ArrayList<>();
        for (int byteIndex = 0; byteIndex < newByte.length; byteIndex++) {
            byte byteValue = newByte[byteIndex];
            for (int bitIndex = 0; bitIndex < 8; bitIndex++) {
                if (((byteValue >> (7 - bitIndex)) & 1) == 1) {
                    // If the bit is 1, calculate the index and add it to the list
                    int index = byteIndex * 8 + bitIndex;
                    indexes.add(index);
                }
            }
        }
        return indexes;
    }



}
