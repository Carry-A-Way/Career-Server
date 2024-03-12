package com.example.career.domain.user.Service;

import com.example.career.domain.user.Entity.StudentDetail;
import com.example.career.domain.user.Repository.StudentDetailRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("낙관적락 MyPoint 감소 테스트")
@SpringBootTest
class StudentDetailMyPointOptimisticLockTest {
    @Autowired
    StudentDetailService studentDetailService;

    @Autowired
    StudentDetailRepository studentDetailRepository;
    private Long savedStudentDetailId;

    @AfterEach
    void deleteTestData() {
        // 테스트 이후에 테스트에 사용된 데이터만 삭제
        if (savedStudentDetailId != null) {
            studentDetailRepository.deleteByStudentId(savedStudentDetailId);
        }
    }
    @Test
    void 낙관적락_MyPoint_감소_테스트() throws InterruptedException {
        StudentDetail savedStudentDetail = 학생_상세정보_생성();
        savedStudentDetailId = savedStudentDetail.getStudentId();
        int numberOfThreads = 3;

        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);

        Future<?> future = executorService.submit(
                () -> {
                    studentDetailService.updateMyPoint(-10, savedStudentDetailId);
                });
        Future<?> future2 = executorService.submit(
                () -> {
                    studentDetailService.updateMyPoint(-20, savedStudentDetailId);
                });
        Future<?> future3 = executorService.submit(
                () -> {
                    studentDetailService.updateMyPoint(-30, savedStudentDetailId);
                });

        Exception result = new Exception();

        try {
            future.get();
            future2.get();
            future3.get();
            // 예외가 발생하지 않았을 경우 테스트 실패
            fail("Expected an OptimisticLockingFailureException to be thrown");
        } catch (ExecutionException e) {
            result = (Exception) e.getCause();
        }

        assertTrue(result instanceof ObjectOptimisticLockingFailureException);
    }

    StudentDetail 학생_상세정보_생성() {
        StudentDetail studentDetail = new StudentDetail();
        studentDetail.setStudentId(2147483647L);
        studentDetail.setMyPoint(100); // 초기 포인트 설정
        studentDetailRepository.save(studentDetail);
        return studentDetail;
    }
}