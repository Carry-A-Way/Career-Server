package com.example.career.domain.user.Dto;

import com.example.career.domain.user.Entity.School;
import com.example.career.domain.user.Entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TagDto {
    private Long idx;
    private String name;

    public Tag toTagEntity(Long id) {
        return Tag.builder()
                .tutorId(id)
                .idx(idx)
                .name(name)
                .build();
    }

    public static TagDto from(Tag tag) {
        if(tag == null) return null;

        return TagDto.builder()
                .idx(tag.getIdx())
                .name(tag.getName())
                .build();
    }
}
