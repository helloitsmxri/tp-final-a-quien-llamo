package com.aquienllamo.aquienllamo.model.mappers;

import com.aquienllamo.aquienllamo.model.dtos.Request.AdministradorDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.AdministradorDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.AdministradorEntity;
import org.springframework.stereotype.Component;

@Component
public class AdministradorMapper {

    public AdministradorEntity toEntity(AdministradorDTORequest dto)
    {
        return AdministradorEntity.builder()
                .nombreUsuario(dto.getNombreUsuario())
                .build();
    }

    public AdministradorDTOResponse toResponse(AdministradorEntity entity)
    {
        return AdministradorDTOResponse.builder()
                .nombreUsuario(entity.getNombreUsuario())
                .build();
    }
}
