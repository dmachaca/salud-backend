package com.pe.model.mapper;

import com.pe.model.dto.request.persona.PersonaInputDto;
import com.pe.model.dto.response.persona.PersonaOutputDto;
import com.pe.model.entity.Persona;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PersonaMapper {

    PersonaOutputDto toOutputDto(Persona persona);

    @Mapping(target = "id", ignore = true)
    Persona toEntity(PersonaInputDto dto);

    void updateEntityFromDto(PersonaInputDto dto, @MappingTarget Persona entity);
}