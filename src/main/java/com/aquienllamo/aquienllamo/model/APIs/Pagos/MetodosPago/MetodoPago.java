package com.aquienllamo.aquienllamo.model.APIs.Pagos.MetodosPago;

import com.aquienllamo.aquienllamo.model.Enum.MetodoDePago;
import com.aquienllamo.aquienllamo.model.dtos.Request.PagoDTORequest;
import com.aquienllamo.aquienllamo.model.entities.PagoEntity;

import java.math.BigDecimal;

public interface MetodoPago {

    boolean soporta(MetodoDePago metodo);

    ResultadoProcesamientoPago procesar(
            PagoDTORequest request,
            PagoEntity pago,
            BigDecimal monto
    );
}
