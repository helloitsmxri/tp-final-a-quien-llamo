package com.aquienllamo.aquienllamo.entidades.caracteristica.mapper;

import com.aquienllamo.aquienllamo.entidades.caracteristica.dtos.request.CaracteristicaDTORequest;
import com.aquienllamo.aquienllamo.entidades.caracteristica.dtos.response.CaracteristicaDTOResponse;
import com.aquienllamo.aquienllamo.entidades.caracteristica.entity.CaracteristicaEntity;
import org.springframework.stereotype.Component;

@Component
public class CaracteristicaMapper {

    // convertir de la request a entity
    public CaracteristicaEntity toEntity(CaracteristicaDTORequest dtoRequest){
        if (dtoRequest == null){
            return null;
        }

        return CaracteristicaEntity.builder()
                .valorAdjetivo(dtoRequest.getValorAdjetivo())
                .tipo(dtoRequest.getTipo())
                .build();
    }

    // convertir entidad a request
    public CaracteristicaDTOResponse toResponse(CaracteristicaEntity entity){
        if (entity == null){
            return null;
        }

        return CaracteristicaDTOResponse.builder()
                .uuid(entity.getUuid())
                .valorAdjetivo(entity.getValorAdjetivo())
                .tipo(entity.getTipo())
                .build();
    }

}
