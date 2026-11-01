package com.aquienllamo.aquienllamo.entidades.habilidad.mapper;

import com.aquienllamo.aquienllamo.entidades.especialidad.entity.EspecialidadEntity;
import com.aquienllamo.aquienllamo.entidades.habilidad.dto.request.HabilidadDTORequest;
import com.aquienllamo.aquienllamo.entidades.habilidad.dto.response.HabilidadCreadaDTOResponse;
import com.aquienllamo.aquienllamo.entidades.habilidad.dto.response.HabilidadDTOResponse;
import com.aquienllamo.aquienllamo.entidades.habilidad.entity.HabilidadEntity;
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

    public HabilidadCreadaDTOResponse toResponseCreada (HabilidadEntity habilidad){
        if (habilidad==null){
            return null;
        }
        return HabilidadCreadaDTOResponse.builder()
                .nombreHabilidad(habilidad.getNombreHabilidad())
                .build();
    }
}
