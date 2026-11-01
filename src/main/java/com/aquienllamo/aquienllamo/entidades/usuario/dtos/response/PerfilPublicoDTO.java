package com.aquienllamo.aquienllamo.entidades.usuario.dtos.response;

import com.aquienllamo.aquienllamo.entidades.rating.dto.response.RatingDTOResponse;

import java.util.List;

// lo utilizamos para devolver los datos que
// CUALQUIER persona puede consultar
public record PerfilPublicoDTO (
        String uuid,
        String nombre,
        String apellido,
        String sobreMi,
        String fotoBase64,
        String tipoImagen,
        String tipoUsuario,
        List<RatingDTOResponse> resenasRecibidas
){

}
