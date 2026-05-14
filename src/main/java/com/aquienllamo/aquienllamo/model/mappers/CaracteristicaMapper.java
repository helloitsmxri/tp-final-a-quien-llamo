package com.aquienllamo.aquienllamo.model.mappers;

import com.aquienllamo.aquienllamo.model.dtos.Request.CaracteristicaDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.CaracteristicaDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.CaracteristicaEntity;
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
                .build();
    }

}
