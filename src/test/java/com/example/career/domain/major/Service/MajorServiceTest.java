package com.example.career.domain.major.Service;

import com.example.career.domain.major.Entity.Major;
import com.example.career.domain.major.Repository.MajorRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
class MajorServiceTest {
    @Mock
    private MajorRepository majorRepository;

    @InjectMocks
    private MajorServiceImpl majorService;

    @Test
    @DisplayName("전공 자동 완성 테스트")
    public void testGetMajorListContaining() {
        // Given
        String majorName = "Computer Science";
        String desc = "";

        Major major1 = Major.builder().majorName("수리과학부").description(desc).build();
        Major major2 = Major.builder().majorName("수학과").description(desc).build();
        Major major3 = Major.builder().majorName("군수학과").description(desc).build();

        List<Major> ans = List.of(major1, major2, major3);

        // Mocking
        given(majorRepository.findByMajorNameContainingOrderByMajorNameAsc("수"))
                .willReturn(ans);

        // When
        List<Major> result = majorService.getMajorListContaining("수");

        // Then
        assertThat(result).hasSameElementsAs(ans);
    }
}