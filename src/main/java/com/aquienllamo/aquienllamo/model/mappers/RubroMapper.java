package com.aquienllamo.aquienllamo.model.mappers;

import com.aquienllamo.aquienllamo.model.dtos.Request.RubroDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.RubroDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.EspecialidadEntity;
import com.aquienllamo.aquienllamo.model.entities.RubroEntity;
import com.aquienllamo.aquienllamo.model.repositories.EspecialidadRepository;
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

        //de entity a response
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
