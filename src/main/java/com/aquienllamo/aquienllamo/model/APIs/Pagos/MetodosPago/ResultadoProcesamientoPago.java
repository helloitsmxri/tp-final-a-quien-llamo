package com.aquienllamo.aquienllamo.model.APIs.Pagos.MetodosPago;

import com.aquienllamo.aquienllamo.model.Enum.EstadoPago;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResultadoProcesamientoPago {

    private EstadoPago estadoPago;
    private String checkoutUrl;
}