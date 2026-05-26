package com.aquienllamo.aquienllamo.model.mappers;

import com.aquienllamo.aquienllamo.model.dtos.Request.RatingDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.RatingDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.CaracteristicaEntity;
import com.aquienllamo.aquienllamo.model.entities.RatingEntity;
import com.aquienllamo.aquienllamo.model.entities.UsuarioEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RatingMapper {

    public static RatingDTOResponse toResponse (RatingEntity entity){
        return RatingDTOResponse.builder()
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
