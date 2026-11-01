package com.aquienllamo.aquienllamo.entidades.pago.mapper;

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
        return PagoDTOResponse.builder()
                .uuid(entity.getUuid())
                .uuidTrabajo(entity.getTrabajo().getUuid())
                .metodoDePago(entity.getMetodoDePago())
                .fechaPago(entity.getFechaPago())
                .estadoPago(entity.getEstadoPago())
                .mercadoPagoOrderId(entity.getMercadoPagoOrderId())
                .build();
    }

    public static PagoDTOResponse toResponse(
            PagoEntity entity,
            String checkoutUrl
    ) {
        PagoDTOResponse response = toResponse(entity);
        response.setCheckoutUrl(checkoutUrl);
        return response;
    }

    public static PagoEntity toEntity(PagoDTORequest request, TrabajoEntity trabajo, BigDecimal monto) {
        return PagoEntity.builder()
                .trabajo(trabajo)
                .monto(monto)
                .metodoDePago(request.getMetodoDePago())
                .estadoPago(EstadoPago.Pendiente_de_revision)
                .build();
    }

}
