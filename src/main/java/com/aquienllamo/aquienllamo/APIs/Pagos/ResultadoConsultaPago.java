package com.aquienllamo.aquienllamo.APIs.Pagos;

import com.aquienllamo.aquienllamo.entidades.pago.enums.EstadoPago;

public record ResultadoConsultaPago(EstadoPago estadoPago, boolean verificado) {
}
