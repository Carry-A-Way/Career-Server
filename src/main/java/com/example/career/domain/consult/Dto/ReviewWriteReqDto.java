package com.example.career.domain.consult.Dto;

import com.example.career.domain.consult.Entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewWriteReqDto {
    private Long id;
    private Long consultId;
    private int rate;
    private String comment;

    public Review toEntity() {
        return Review.builder()
                .consultId(consultId)
                .rate(rate)
                .comment(comment)
                .build();
    }
}
