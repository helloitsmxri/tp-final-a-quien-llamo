package com.aquienllamo.aquienllamo.model.APIs.Pagos;

import com.aquienllamo.aquienllamo.model.Enum.EstadoPago;

public record ResultadoConsultaPago(EstadoPago estadoPago, boolean verificado) {
}
