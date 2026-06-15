package com.aquienllamo.aquienllamo.model.mappers;

import com.aquienllamo.aquienllamo.model.dtos.Request.TrabajoDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.TrabajoDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.PresupuestoEntity;
import com.aquienllamo.aquienllamo.model.entities.TrabajoEntity;
import org.springframework.stereotype.Component;

@Component
public class TrabajoMapper {

    public static TrabajoDTOResponse toResponse (TrabajoEntity entity){
        return TrabajoDTOResponse.builder()
                .uuid(entity.getUuid())
                .uuidPresupuesto(entity.getPresupuesto().getUuid())
                .descripcionTrabajo(entity.getDescripcionTrabajo())
                .fechaEstimadaInicio(entity.getFechaEstimadaInicio())
                .fechaEstimadaFin(entity.getFechaEstimadaFin())
                .estadoTrabajo(entity.getEstadoTrabajo())
                .build();
    }

    public static TrabajoEntity toEntity(TrabajoDTORequest request, PresupuestoEntity presupuesto){
        return TrabajoEntity.builder()
                .presupuesto(presupuesto)
                .descripcionTrabajo(request.getDescripcionTrabajo())
                .fechaEstimadaInicio(request.getFechaEstimadaInicio())
                .fechaEstimadaFin(request.getFechaEstimadaFin())
                .estadoTrabajo(request.getEstadoTrabajo())
                .build();
    }

}
