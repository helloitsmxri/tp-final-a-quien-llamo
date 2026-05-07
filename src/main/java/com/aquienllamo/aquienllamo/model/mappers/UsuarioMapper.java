package com.aquienllamo.aquienllamo.model.mappers;

import com.aquienllamo.aquienllamo.model.dtos.Request.UsuarioDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.UsuarioDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.UsuarioEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Base64;

@Component
public class UsuarioMapper {

    // convertir de la request a una entidad
    public UsuarioEntity toEntity (UsuarioDTORequest dtoRequest){
        if (dtoRequest == null){
            return null;
        }

        return UsuarioEntity.builder()
                .dni(dtoRequest.getDni())
                .nombre(dtoRequest.getNombre())
                .apellido(dtoRequest.getApellido())
                .clave(dtoRequest.getClave())
                .email(dtoRequest.getEmail())
                .telefono(dtoRequest.getTelefono())
                .fechaNacimiento(dtoRequest.getFechaNacimiento())
                .sobreMi(dtoRequest.getSobreMi())
                .ultimaActividad(LocalDateTime.now())
                .build();
    }
    // convertir la entidad a una response
    public UsuarioDTOResponse toResponse(UsuarioEntity user){
        if (user == null){
            return null;
        }

        String fotoBase64 = null;
        if (user.getFoto() != null && user.getFoto().length > 0) {
            fotoBase64 = Base64.getEncoder().encodeToString(user.getFoto());
        }

        return UsuarioDTOResponse.builder()
                .uuid(user.getUuid())
                .nombre(user.getNombre())
                .apellido(user.getApellido())
                .email(user.getEmail())
                .telefono(user.getTelefono())
                .sobreMi(user.getSobreMi())
                .fotoBase64(fotoBase64)
                .fechaRegistro(user.getFechaRegistro())
                .ultimaActividad(user.getUltimaActividad())
                .build();
    }
}
