package com.example.career.domain.major.Repository;

import com.example.career.domain.major.Entity.Major;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class MajorRepositoryTest {
    @Autowired
    private MajorRepository majorRepository;

    @Test
    @DisplayName("major name이 잘 포함되어 있는지 확인")
    void findContaining() {
        // given
        String majorName = "수리과학부";
        String desc = "test";
        Major major1 = Major.builder().majorName("수리과학부").description(desc).build();
        Major major2 = Major.builder().majorName("수학과").description(desc).build();
        Major major3 = Major.builder().majorName("군수학과").description(desc).build();
        Major major4 = Major.builder().majorName("전산학부").description(desc).build();
        Major major5 = Major.builder().majorName("전기및전자공학부").description(desc).build();

        List<Major> majors = List.of(major1, major2, major3, major4, major5);
        List<Major> ans = List.of(major1, major2, major3);
        majorRepository.saveAll(majors);

        // when
        List<Major> byMajorNameContaining = majorRepository.findByMajorNameContainingOrderByMajorNameAsc("수");
        List<Major> all = majorRepository.findByMajorNameContainingOrderByMajorNameAsc("");

        // then
        assertThat(byMajorNameContaining).hasSameElementsAs(ans);
        assertThat(all).hasSameElementsAs(majors);
    }

}