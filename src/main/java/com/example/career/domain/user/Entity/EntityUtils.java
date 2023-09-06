package com.example.career.domain.user.Entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class EntityUtils {
    @FunctionalInterface
    public interface DtoToEntityFunction<DTO, ENTITY> {
        ENTITY apply(DTO dto, Long id);
    }

    @FunctionalInterface
    public interface RepositoryFindFunction<ENTITY> {
        Optional<ENTITY> find(Long id, Long idx);
    }

    @FunctionalInterface
    public interface UpdateFunction<ENTITY, DTO> {
        void update(ENTITY entity, DTO dto, Set<String> fields, boolean isUpdate);
    }

    @FunctionalInterface
    public interface DtoIdxExtractor<DTO> {
        Long extract(DTO dto);
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

    public static <DTO, ENTITY> void processEntities(List<DTO> dtos,
                                              Long id,
                                              JpaRepository<ENTITY, Long> repository,
                                              DtoToEntityFunction<DTO, ENTITY> function,
                                              RepositoryFindFunction<ENTITY> findFunction,
                                              UpdateFunction<ENTITY, DTO> updateFunction,
                                              DtoIdxExtractor<DTO> idxExtractor) {
        if (dtos != null) {
            for (DTO dto : dtos) {
                Long idx = idxExtractor.extract(dto);
                Optional<ENTITY> entityOpt = findFunction.find(id, idx);
                if (entityOpt.isPresent()) {
                    updateFunction.update(entityOpt.get(), dto, null, true);
                } else {
                    repository.save(function.apply(dto, id));
                }
            }
        }
    }
}
