package com.aquienllamo.aquienllamo.model.mappers;

import com.aquienllamo.aquienllamo.model.dtos.Request.PresupuestoDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.PresupuestoDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.PresupuestoEntity;
import org.springframework.stereotype.Component;

@Component
public class PresupuestoMapper {
    // convertir de la request a una entidad
    public PresupuestoEntity toEntity(PresupuestoDTORequest presupuesto){
       // verificar si no viene vacío:
        if (presupuesto == null){
            return null;
        }

        return PresupuestoEntity.builder()
                .descripcionPresupuesto(presupuesto.getDescripcionPresupuesto())
                .precioEstimado(presupuesto.getPrecioEstimado())
                .build();
    }
    // convertir la entidad a una response
    public PresupuestoDTOResponse toResponse(PresupuestoEntity presupuesto){
        if (presupuesto == null){
            return null;
        }

        return PresupuestoDTOResponse.builder().uuid(presupuesto.getUuid())
                .nombreTecnico(presupuesto.getTecnico().getUsuario().getNombre())
                .apellidoTecnico(presupuesto.getTecnico().getUsuario().getApellido())
                .nombreUsuario(presupuesto.getUsuario().getNombre())
                .apellidoUsuario(presupuesto.getUsuario().getApellido())
                .precioEstimado(presupuesto.getPrecioEstimado())
                .descripcionPresupuesto(presupuesto.getDescripcionPresupuesto())
                .estado(presupuesto.getEstado())
                .fechaRealizado(presupuesto.getFechaRealizado())
                .build();
    }
}
