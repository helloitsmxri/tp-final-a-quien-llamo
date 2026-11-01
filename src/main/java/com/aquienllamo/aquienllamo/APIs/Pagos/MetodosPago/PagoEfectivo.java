package com.aquienllamo.aquienllamo.APIs.Pagos.MetodosPago;

import com.aquienllamo.aquienllamo.entidades.pago.enums.EstadoPago;
import com.aquienllamo.aquienllamo.entidades.pago.enums.MetodoDePago;
import com.aquienllamo.aquienllamo.entidades.pago.dto.request.PagoDTORequest;
import com.aquienllamo.aquienllamo.entidades.pago.entity.PagoEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PagoEfectivo implements MetodoPago {

    @Override
    public ResultadoProcesamientoPago procesar(
            PagoDTORequest request,
            PagoEntity pago,
            BigDecimal monto) {

        return new ResultadoProcesamientoPago(
                EstadoPago.Pendiente_de_revision,
                null
        );
    }

    @Override
    public boolean soporta(MetodoDePago metodo) {
        return metodo == MetodoDePago.Efectivo;
    }
}
