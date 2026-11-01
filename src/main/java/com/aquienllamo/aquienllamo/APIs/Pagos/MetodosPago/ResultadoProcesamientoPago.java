package com.aquienllamo.aquienllamo.APIs.Pagos.MetodosPago;

import com.aquienllamo.aquienllamo.entidades.pago.enums.EstadoPago;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResultadoProcesamientoPago {

    private EstadoPago estadoPago;
    private String checkoutUrl;
}