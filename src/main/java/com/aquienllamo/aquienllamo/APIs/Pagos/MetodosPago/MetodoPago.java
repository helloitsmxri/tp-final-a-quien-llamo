package com.aquienllamo.aquienllamo.APIs.Pagos.MetodosPago;

import com.aquienllamo.aquienllamo.entidades.pago.enums.MetodoDePago;
import com.aquienllamo.aquienllamo.entidades.pago.dto.request.PagoDTORequest;
import com.aquienllamo.aquienllamo.entidades.pago.entity.PagoEntity;

import java.math.BigDecimal;

public interface MetodoPago {

    boolean soporta(MetodoDePago metodo);

    ResultadoProcesamientoPago procesar(
            PagoDTORequest request,
            PagoEntity pago,
            BigDecimal monto
    );
}
