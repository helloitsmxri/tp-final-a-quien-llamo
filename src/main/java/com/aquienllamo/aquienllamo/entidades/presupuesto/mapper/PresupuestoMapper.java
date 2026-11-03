
package com.aquienllamo.aquienllamo.entidades.presupuesto.mapper;

import com.aquienllamo.aquienllamo.entidades.presupuesto.dto.response.PresupuestoDTOResponse;
import com.aquienllamo.aquienllamo.entidades.presupuesto.dto.response.PresupuestoDetalleDTOResponse;
import com.aquienllamo.aquienllamo.entidades.presupuesto.entity.PresupuestoEntity;
import com.aquienllamo.aquienllamo.entidades.presupuesto.entity.PresupuestoDetalleEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class PresupuestoMapper {

    // Convertir una entidad de presupuesto a su DTO de respuesta.
    public PresupuestoDTOResponse toResponse(PresupuestoEntity presupuesto) {

        if (presupuesto == null) {
            return null;
        }

        List<PresupuestoDetalleDTOResponse> detalles = presupuesto.getDetalles()
                .stream()
                .map(this::detalleToResponse)
                .toList();

        return PresupuestoDTOResponse.builder()
                .uuid(presupuesto.getUuid())

                .nombreTecnico(presupuesto.getTecnico().getUsuario().getNombre())
                .apellidoTecnico(presupuesto.getTecnico().getUsuario().getApellido())

                .nombreUsuario(presupuesto.getUsuario().getNombre())
                .apellidoUsuario(presupuesto.getUsuario().getApellido())

                .nombreProyecto(presupuesto.getNombreProyecto())
                .observaciones(presupuesto.getObservaciones())

                .detalles(detalles)

                .subtotal(presupuesto.getSubtotal())
                .descuentoPorcentaje(presupuesto.getDescuentoPorcentaje())
                .montoDescuento(presupuesto.getMontoDescuento())
                .precioEstimado(presupuesto.getPrecioEstimado())

                .estado(presupuesto.getEstado())
                .fechaRealizado(presupuesto.getCreatedAt())
                .build();
    }

    // Convertir cada concepto del presupuesto.
    private PresupuestoDetalleDTOResponse detalleToResponse(
            PresupuestoDetalleEntity detalle) {

        BigDecimal total = detalle.getPrecioUnitario()
                .multiply(BigDecimal.valueOf(detalle.getCantidad()));

        return PresupuestoDetalleDTOResponse.builder()
                .descripcion(detalle.getDescripcion())
                .precioUnitario(detalle.getPrecioUnitario())
                .cantidad(detalle.getCantidad())
                .total(total)
                .build();
    }
}
