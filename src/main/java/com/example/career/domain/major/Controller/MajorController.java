package com.example.career.domain.major.Controller;

import com.example.career.domain.major.Dto.MajorNameRespDto;
import com.example.career.domain.major.Dto.MajorReqDto;
import com.example.career.domain.major.Entity.Major;
import com.example.career.domain.major.Repository.MajorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("major")
public class MajorController {
    private final MajorRepository majorRepository;

    @GetMapping("get/all")
    public List<Major> getMajorAll() {
        return majorRepository.findAll();
    }
    @GetMapping("get/name")
    public List<MajorNameRespDto> getMajorListOfName() {
        return majorRepository.findAll().stream().map(Major::toNameDto).toList();
    }
    @PostMapping("insert")
    public boolean insertMajor(@RequestBody MajorReqDto majors) {
        System.out.println("전공 추가");
        try {
            majorRepository.saveAll(majors.getMajors()  );
            return true;
        } catch (Exception e) {
            // 기타 예외 처리
            e.printStackTrace();
            return false;
        }

    }

}
