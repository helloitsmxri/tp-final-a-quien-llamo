package com.aquienllamo.aquienllamo.entidades.pago.dto.response;

import com.aquienllamo.aquienllamo.entidades.pago.enums.EstadoPago;
import com.aquienllamo.aquienllamo.entidades.pago.enums.MetodoDePago;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PagoDTOResponse {

    private String uuid; //id del pago generado

    private String uuidTrabajo; //id del trabajo que se abono

    private MetodoDePago metodoDePago;

    private LocalDateTime fechaPago;

    private EstadoPago estadoPago;

    private String checkoutUrl;

    private String mercadoPagoOrderId;

}
