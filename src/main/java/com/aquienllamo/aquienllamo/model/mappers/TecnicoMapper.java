package com.aquienllamo.aquienllamo.model.mappers;

import com.aquienllamo.aquienllamo.model.dtos.Request.TecnicoDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.TecnicoDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.TecnicoEntity;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.List;

@Component
public class TecnicoMapper {

    public TecnicoDTOResponse toResponse(TecnicoEntity tecnico){
        if (tecnico == null){
            return null;
        }

        //foto del usuario
        String fotoBase64=null;
        if (tecnico.getUsuario().getFoto()!=null){
            fotoBase64= Base64.getEncoder().encodeToString(tecnico.getUsuario().getFoto());
        }

        //listas
        List<String> habilidades=tecnico.getHabilidades().stream()
                .map(h->h.getNombreHabilidad())
                .toList();

        List<String> especialidades=tecnico.getEspecialidades().stream()
                .map(e->e.getNombreEspecialidad())
                .toList();

        return TecnicoDTOResponse.builder()
                .uuid(tecnico.getUuid())
                .nombre(tecnico.getUsuario().getNombre())
                .apellido(tecnico.getUsuario().getApellido())
                .email(tecnico.getUsuario().getEmail())
                .telefono(tecnico.getUsuario().getTelefono())
                .sobreMi(tecnico.getUsuario().getSobreMi())
                .fotoBase64(fotoBase64)
                .tipoImagen(tecnico.getUsuario().getTipoImagen())
                .cuit(tecnico.getCuit())
                .descripcionTrabajo(tecnico.getDescripcionTrabajo())
                .proyectos(tecnico.getProyectos())
                .habilidades(habilidades)
                .especialidades(especialidades)
                .build();
    }

    public TecnicoEntity toEntity(TecnicoDTORequest dto){
        if (dto == null){
            return null;
        }
        return TecnicoEntity.builder()
                .cuit(dto.getCuit())
                .descripcionTrabajo(dto.getDescripcionTrabajo())
                .proyectos(dto.getProyectos())
                .build();
    }
}
