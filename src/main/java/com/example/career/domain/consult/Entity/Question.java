//package com.example.career.domain.consult.Entity;
//
//import com.example.career.domain.consult.Dto.QueryRespDto;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//@Builder
//@Table(name = "Question")
//public class Question
////        implements UserDetails
//{
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private Long id;
//
//    @Column(columnDefinition = "TEXT")
//    String flow;
//
//    @Column(columnDefinition = "TEXT")
//    String query1;
//
//    @Column(columnDefinition = "TEXT")
//    String query2;
//
//    @Column(columnDefinition = "TEXT")
//    String etc;
//
//    public QueryRespDto toQueryRespDto() {
//        return QueryRespDto.builder()
//                .id(id)
//                .flow(flow)
//                .query1(query1)
//                .query2(query2)
//                .etc(etc)
//                .build();
//    }
//}
