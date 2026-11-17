package com.aquienllamo.aquienllamo.entidades.pago.mapper;

import com.aquienllamo.aquienllamo.entidades.pago.enums.EstadoPago;
import com.aquienllamo.aquienllamo.entidades.pago.dto.request.PagoDTORequest;
import com.aquienllamo.aquienllamo.entidades.pago.dto.response.PagoDTOResponse;
import com.aquienllamo.aquienllamo.entidades.pago.entity.PagoEntity;
import com.aquienllamo.aquienllamo.entidades.trabajo.entity.TrabajoEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import com.aquienllamo.aquienllamo.entidades.pago.enums.EstadoPago;
import com.aquienllamo.aquienllamo.entidades.pago.dto.request.PagoDTORequest;
import com.aquienllamo.aquienllamo.entidades.pago.dto.response.PagoDTOResponse;
import com.aquienllamo.aquienllamo.entidades.pago.entity.PagoEntity;
import com.aquienllamo.aquienllamo.entidades.trabajo.entity.TrabajoEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PagoMapper {

    public static PagoDTOResponse toResponse(PagoEntity entity) {

        var datosProveedor = entity.getDatosProveedor();

        return PagoDTOResponse.builder()
                .uuid(entity.getUuid())
                .uuidTrabajo(entity.getTrabajo().getUuid())

                .nombreProyecto(entity.getTrabajo()
                        .getPresupuesto()
                        .getNombreProyecto())

                .descripcionProyecto(entity.getTrabajo()
                        .getPresupuesto()
                        .getDescripcionPresupuesto())

                .proveedorDePago(entity.getProveedorDePago())
                .metodoDePago(entity.getMetodoDePago())
                .fechaPago(entity.getFechaPago())
                .estadoPago(entity.getEstadoPago())

                .checkoutUrl(datosProveedor != null
                        ? datosProveedor.getCheckoutUrl()
                        : null)

                .externalPaymentId(datosProveedor != null
                        ? datosProveedor.getExternalPaymentId()
                        : null)

                .estadoExterno(datosProveedor != null
                        ? datosProveedor.getEstadoExterno()
                        : null)

                .build();
    }

    public static PagoEntity toEntity(
            PagoDTORequest request,
            TrabajoEntity trabajo,
            BigDecimal monto
    ) {
        return PagoEntity.builder()
                .trabajo(trabajo)
                .monto(monto)
                .proveedorDePago(request.getProveedorDePago())
                .metodoDePago(request.getMetodoDePago())
                .estadoPago(EstadoPago.Pendiente_de_revision)
                .build();
    }
}