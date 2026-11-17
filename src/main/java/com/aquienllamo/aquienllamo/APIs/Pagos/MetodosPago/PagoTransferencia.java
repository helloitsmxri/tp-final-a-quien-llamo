
package com.aquienllamo.aquienllamo.APIs.Pagos.MetodosPago;

import com.aquienllamo.aquienllamo.APIs.Pagos.ProveedorPago;
import com.aquienllamo.aquienllamo.entidades.pago.enums.MetodoDePago;
import com.aquienllamo.aquienllamo.entidades.pago.dto.request.PagoDTORequest;
import com.aquienllamo.aquienllamo.entidades.pago.entity.PagoEntity;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PagoTransferencia implements MetodoPago {

    @Override
    public ResultadoProcesamientoPago procesar(
            PagoDTORequest request,
            PagoEntity pago,
            BigDecimal monto,
            ProveedorPago proveedor
    ) {

        return proveedor.crearCheckout(
                request,
                pago,
                monto
        );
    }

    @Override
    public boolean soporta(MetodoDePago metodo) {
        return metodo == MetodoDePago.Transferencia;
    }
}
