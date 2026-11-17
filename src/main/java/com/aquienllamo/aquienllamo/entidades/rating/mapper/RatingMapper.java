package com.aquienllamo.aquienllamo.entidades.rating.mapper;

import com.aquienllamo.aquienllamo.entidades.caracteristica.entity.CaracteristicaEntity;
import com.aquienllamo.aquienllamo.entidades.rating.dto.request.RatingDTORequest;
import com.aquienllamo.aquienllamo.entidades.rating.dto.response.RatingDTOResponse;
import com.aquienllamo.aquienllamo.entidades.rating.entity.RatingEntity;
import com.aquienllamo.aquienllamo.entidades.usuario.entities.UsuarioEntity;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.List;

@Component
public class RatingMapper {

    public static RatingDTOResponse toResponse(RatingEntity entity) {
        UsuarioEntity remitente = entity.getUsuarioRemitente();

        String fotoBase64 = (remitente.getFoto() != null && remitente.getFoto().length > 0)
                ? Base64.getEncoder().encodeToString(remitente.getFoto())
                : null;

        return RatingDTOResponse.builder()
                .uuidCalificacion(entity.getUuid())
                .uuidRemitente(remitente.getUuid())
                .nombreRemitente(remitente.getNombre())
                .apellidoRemitente(remitente.getApellido())
                .nombreDestinatario(entity.getUsuarioDestinatario().getNombre())
                .valoracion(entity.getValoracion())
                .descripcion(entity.getDescripcion())
                .tipoFoto(remitente.getTipoImagen())
                .foto(fotoBase64)
                .caracteristicas(entity.getCaracteristicas()
                        .stream()
                        .map(CaracteristicaEntity::getValorAdjetivo)
                        .toList())
                .build();
    }

    public static RatingEntity toEntity (RatingDTORequest request, List<CaracteristicaEntity> caracteristica, UsuarioEntity remitente, UsuarioEntity destinatario){
        return RatingEntity.builder()
                .usuarioRemitente(remitente)
                .usuarioDestinatario(destinatario)
                .valoracion(request.getValoracion())
                .descripcion(request.getDescripcion())
                .tipoFoto(request.getTipoFoto())
                .foto(request.getFoto())
                .caracteristicas(caracteristica)
                .build();
    }

}
