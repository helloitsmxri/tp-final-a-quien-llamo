package com.aquienllamo.aquienllamo.entidades.rating.mapper;

import com.aquienllamo.aquienllamo.entidades.caracteristica.entity.CaracteristicaEntity;
import com.aquienllamo.aquienllamo.entidades.rating.dto.request.RatingDTORequest;
import com.aquienllamo.aquienllamo.entidades.rating.dto.response.RatingDTOResponse;
import com.aquienllamo.aquienllamo.entidades.rating.entity.RatingEntity;
import com.aquienllamo.aquienllamo.entidades.usuario.entities.UsuarioEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RatingMapper {

    public static RatingDTOResponse toResponse (RatingEntity entity){
        return RatingDTOResponse.builder()
                .uuidRemitente(entity.getUsuarioRemitente().getUuid())
                .nombreRemitente(entity.getUsuarioRemitente().getNombre())
                .nombreDestinatario(entity.getUsuarioDestinatario().getNombre())
                .valoracion(entity.getValoracion())
                .descripcion(entity.getDescripcion())
                .tipoFoto(entity.getTipoFoto())
                .foto(entity.getFoto())
                .caracteristicas(entity.getCaracteristicas()
                        .stream()
                        .map(CaracteristicaEntity::getValorAdjetivo) //el nombre de la caracteristica
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
