package com.example.career.domain.major.Controller;

import com.example.career.domain.major.Entity.Major;
import com.example.career.domain.major.Repository.MajorRepository;
import com.example.career.domain.major.Service.MajorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
//@WebMvcTest(MajorController.class)
class MajorControllerTest {
    private MockMvc mockMvc;

    @MockBean
    private MajorService majorService;
    @InjectMocks
    private MajorController majorController;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(majorController).build();// TODO: Make sure to set the field name in UUT correctly
        ReflectionTestUtils.setField( mockMvc, "majorService", majorService );
    }

//    @Test
//    @DisplayName("전공명 자동 완성 Contoller")
//    public void testSearchContainedMajorName() throws Exception {
//        // Given
//        String desc = "";
//        String query = "수";
//        Major major1 = Major.builder().majorName("수리과학부").description(desc).build();
//        Major major2 = Major.builder().majorName("수학과").description(desc).build();
//        Major major3 = Major.builder().majorName("군수학과").description(desc).build();
//
//        List<Major> ans = List.of(major1, major2, major3);
//
////        given(majorService.getMajorListContaining(query))
////                .willReturn(ans);
//        when(majorService.getMajorListContaining(any(String.class))).thenReturn(ans);
//
//        // Act
//        ResultActions resultActions = mockMvc.perform(get("/major/contains")
//                .param("majorName", query)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print());
//
//        // Assert
//        resultActions.andExpect(status().isOk())
//                .andExpect(jsonPath("$").isArray())
//                .andExpect(jsonPath("$[0]").value(ans.get(0)));
//    }
//
//    @Test
//    public void testInsertaMajor() throws Exception {
//        // Given
//        Major mockMajor = new Major("TestMajor");
//
//        // When and Then
//        mockMvc.perform(post("/major/insert_one")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(mockMajor)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.majorName").value(mockMajor.getMajorName()));
//    }
//
//    // Helper method to convert objects to JSON string
//    private String asJsonString(final Object obj) {
//        try {
//            return new ObjectMapper().writeValueAsString(obj);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
}