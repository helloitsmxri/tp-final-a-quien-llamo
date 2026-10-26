package com.aquienllamo.aquienllamo.model.APIs.Pagos.MetodosPago;

import com.aquienllamo.aquienllamo.model.Enum.EstadoPago;
import com.aquienllamo.aquienllamo.model.Enum.MetodoDePago;
import com.aquienllamo.aquienllamo.model.dtos.Request.PagoDTORequest;
import com.aquienllamo.aquienllamo.model.entities.PagoEntity;
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
