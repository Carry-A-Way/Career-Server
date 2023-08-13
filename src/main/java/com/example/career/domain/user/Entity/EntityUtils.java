package com.example.career.domain.user.Entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public class EntityUtils {
    @FunctionalInterface
    public interface DtoToEntityFunction<DTO, ENTITY> {
        ENTITY apply(DTO dto, Long id);
    }
    public static <DTO, ENTITY> void saveEntities(List<DTO> dtos, Long id,
                                                  JpaRepository<ENTITY, ?> repository,
                                                  DtoToEntityFunction<DTO, ENTITY> function) {
        if (dtos == null) {
            return;
        }
        for (DTO dto : dtos) {
            ENTITY entity = function.apply(dto, id);
            repository.save(entity);
        }
    }
}
