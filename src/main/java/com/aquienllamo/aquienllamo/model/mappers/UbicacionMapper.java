package com.aquienllamo.aquienllamo.model.mappers;

import com.aquienllamo.aquienllamo.model.dtos.Request.UbicacionDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.UbicacionDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.UbicacionEntity;
import org.springframework.stereotype.Component;

@Component // le dice a Spring que esa clase es un bean.
public class UbicacionMapper {

    public UbicacionDTOResponse toResponse(UbicacionEntity ubicacion)
    {
        if(ubicacion == null)
        {
            return null;
        }
        return UbicacionDTOResponse.builder() //patron builder
                .uuid(ubicacion.getUuid())
                .codigoPostal(ubicacion.getCodigoPostal())
                .provincia(ubicacion.getProvincia())
                .ciudad(ubicacion.getCiudad())
                .calle(ubicacion.getCalle())
                .numero(ubicacion.getNumero())
                .piso(ubicacion.getPiso())
                .numeroPiso(ubicacion.getNumeroPiso())
                .build();
    }

    public UbicacionEntity toEntity(UbicacionDTORequest dto)
    {
        if(dto == null)
        {
            return null;
        }
        return UbicacionEntity.builder()
                .codigoPostal(dto.getCodigoPostal())
                .provincia(dto.getProvincia())
                .ciudad(dto.getCiudad())
                .calle(dto.getCalle())
                .numero(dto.getNumero())
                .piso(dto.getPiso())
                .numeroPiso(dto.getNumeroPiso())
                .build();
    }



}
