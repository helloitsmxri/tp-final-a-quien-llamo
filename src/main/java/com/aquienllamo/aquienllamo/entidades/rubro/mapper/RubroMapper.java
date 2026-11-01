package com.aquienllamo.aquienllamo.entidades.rubro.mapper;

import com.aquienllamo.aquienllamo.entidades.especialidad.entity.EspecialidadEntity;
import com.aquienllamo.aquienllamo.entidades.especialidad.repository.EspecialidadRepository;
import com.aquienllamo.aquienllamo.entidades.rubro.dto.request.RubroDTORequest;
import com.aquienllamo.aquienllamo.entidades.rubro.dto.response.RubroDTOResponse;
import com.aquienllamo.aquienllamo.entidades.rubro.entity.RubroEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RubroMapper {

    private final EspecialidadRepository especialidadRepository;

    //de dto request a entity
    public RubroEntity toEntity(RubroDTORequest dtoRequest){

        //buscar las especialidades en la bdd
        List<EspecialidadEntity> especialidades = dtoRequest.getUuidEspecialidad()
                .stream()
                .map(uuid -> especialidadRepository
                        .findByUuid(uuid)
                        .orElseThrow())
                .toList();

        //crear una entity
        return RubroEntity.builder()
                .nombreRubro(dtoRequest.getNombreRubro())
                .especialidades(especialidades)
                .build();

    }

    //de entity a dto response
    public RubroDTOResponse toResponseRubro(RubroEntity rubroEntity){

        //de entity, response
        return RubroDTOResponse.builder()
                .uuidRubro(rubroEntity.getUuid())
                .nombreRubro(rubroEntity.getNombreRubro())
                .especialidades(rubroEntity.getEspecialidades()
                        .stream()
                        .map(EspecialidadEntity::getNombreEspecialidad)
                        .toList())
                .build();

    }

}
