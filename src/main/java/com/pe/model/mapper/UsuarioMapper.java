package com.pe.model.mapper;

import com.pe.model.dto.request.usuario.UsuarioInputDto;
import com.pe.model.dto.response.usuario.UsuarioOutputDto;
import com.pe.model.entity.Usuario;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UsuarioMapper {

    @Mapping(source = "persona.id", target = "personaId")
    @Mapping(source = "persona.nombres", target = "nombres")
    @Mapping(source = "persona.apellidoPaterno", target = "apellidoPaterno")
    @Mapping(source = "persona.apellidoMaterno", target = "apellidoMaterno")
    @Mapping(source = "persona.dni", target = "dni")
    UsuarioOutputDto toOutputDto(Usuario usuario);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "persona", ignore = true)
    @Mapping(target = "claveHash", ignore = true)
    Usuario toEntity(UsuarioInputDto dto);

    @Mapping(target = "persona", ignore = true)
    @Mapping(target = "claveHash", ignore = true)
    void updateEntityFromDto(UsuarioInputDto dto, @MappingTarget Usuario entity);
}