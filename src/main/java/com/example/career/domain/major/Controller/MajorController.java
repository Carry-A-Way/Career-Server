package com.example.career.domain.major.Controller;

import com.example.career.domain.major.Dto.MajorNameRespDto;
import com.example.career.domain.major.Dto.MajorReqDto;
import com.example.career.domain.major.Entity.Major;
import com.example.career.domain.major.Repository.MajorRepository;
import com.example.career.domain.major.Service.MajorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("major")
public class MajorController {
    private final MajorRepository majorRepository;
    private final MajorService majorService;

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

    @GetMapping("contains")
    public ResponseEntity<List<String>> searchContainedMajorName(@RequestParam String majorName) {
        List<String> list = majorService.getMajorListContaining(majorName)
                .stream()
                .map(Major::getMajorName)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @PostMapping("insert_one")
    public ResponseEntity<Major> insertaMajor (@RequestBody Major majorE) {
        return ResponseEntity.ok(majorRepository.save(majorE));
    }
}
