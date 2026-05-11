package com.aquienllamo.aquienllamo.model.mappers;

import com.aquienllamo.aquienllamo.model.dtos.Request.EspecialidadDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.EspecialidadDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.EspecialidadEntity;
import com.aquienllamo.aquienllamo.model.entities.HabilidadEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EspecialidadMapper {

    public EspecialidadDTOResponse toResponse(EspecialidadEntity especialidad) {
        if (especialidad == null) {
            return null;
        }

        //extraigo los nombres de habilidades
        List<String> habilidades=null;
        if(especialidad.getHabilidades()!=null){
            habilidades=especialidad.getHabilidades().stream()
                    .map(h->h.getNombreHabilidad())
                    .toList();
        }

        //extraigo los nombres de rubros
        List<String> rubros=null;
        if(especialidad.getRubros()!=null){
            rubros=especialidad.getRubros().stream()
                    .map(r->r.getNombreRubro())
                    .toList();
        }

        return EspecialidadDTOResponse.builder()
                .uuid(especialidad.getUuid())
                .nombreEspecialidad(especialidad.getNombreEspecialidad())
                .tipoValidacion(especialidad.getTipoValidacion())
                .habilidades(habilidades)
                .rubros(rubros)
                .build();
    }

    public EspecialidadEntity toEntity(EspecialidadDTORequest dto){
        if (dto == null) {
            return null;
        }

        return EspecialidadEntity.builder()
                .nombreEspecialidad(dto.getNombreEspecialidad())
                .tipoValidacion(dto.getTipoValidacion())
                .build();
    }
}
