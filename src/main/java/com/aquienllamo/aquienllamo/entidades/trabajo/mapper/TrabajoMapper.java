package com.aquienllamo.aquienllamo.entidades.trabajo.mapper;

import com.aquienllamo.aquienllamo.entidades.presupuesto.entity.PresupuestoEntity;
import com.aquienllamo.aquienllamo.entidades.trabajo.dto.request.TrabajoDTORequest;
import com.aquienllamo.aquienllamo.entidades.trabajo.dto.response.TrabajoDTOResponse;
import com.aquienllamo.aquienllamo.entidades.trabajo.entity.TrabajoEntity;
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
