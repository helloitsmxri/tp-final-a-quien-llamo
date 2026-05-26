package com.aquienllamo.aquienllamo.model.mappers;

import com.aquienllamo.aquienllamo.model.Enum.Estado;
import com.aquienllamo.aquienllamo.model.dtos.Request.PagoDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.PagoDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.PagoEntity;
import com.aquienllamo.aquienllamo.model.entities.TrabajoEntity;
import org.springframework.stereotype.Component;

@Component
public class PagoMapper {

    public static PagoDTOResponse toResponse (PagoEntity entity){
        return PagoDTOResponse.builder()
                .uuid(entity.getUuid())
                .uuidTrabajo(entity.getTrabajo().getUuid())
                .metodoDePago(entity.getMetodoDePago())
                .fechaPago(entity.getFechaPago())
                .estadoPago(entity.getEstadoPago())
                .build();
    }

    public static PagoEntity toEntity (PagoDTORequest request, TrabajoEntity trabajo){
        return PagoEntity.builder()
                .trabajo(trabajo)
                .metodoDePago(request.getMetodoDePago())
                .estadoPago(Estado.Pendiente_de_revision) //ingresa el pago y queda en revision
                .build();
    }

}
