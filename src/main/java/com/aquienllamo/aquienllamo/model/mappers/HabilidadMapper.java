package com.aquienllamo.aquienllamo.model.mappers;

import com.aquienllamo.aquienllamo.model.dtos.Request.HabilidadDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.HabilidadDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.EspecialidadEntity;
import com.aquienllamo.aquienllamo.model.entities.HabilidadEntity;
import org.springframework.stereotype.Component;

@Component
public class HabilidadMapper {
    public HabilidadDTOResponse toResponse (HabilidadEntity habilidad){
        if (habilidad==null){
            return null;
        }
        return HabilidadDTOResponse.builder()
                .uuid(habilidad.getUuid())
                .nombreHabilidad(habilidad.getNombreHabilidad())
                .especialidades(habilidad.getEspecialidades().stream()
                        .map(EspecialidadEntity::getNombreEspecialidad).toList())
                .build();
    }

    public HabilidadEntity toEntity(HabilidadDTORequest dto){
        if (dto==null){
            return null;
        }
        return HabilidadEntity.builder()
                .nombreHabilidad(dto.getNombreHabilidad())
                .build();
    }
}
