package com.example.career.domain.community.Dto;

import com.example.career.domain.community.Entity.Article;
import com.example.career.domain.community.Entity.Heart;
import com.example.career.domain.user.Entity.User;
import lombok.*;

@Data
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HeartDto {
    private Long typeId;
    private int type;
    public Heart toHeartEntity(User user) {
        return Heart.builder()
                .user(user)
                .typeId(typeId)
                .type(type)
                .build();

    }
}
