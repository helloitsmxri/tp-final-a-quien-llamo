package com.aquienllamo.aquienllamo.APIs.Pagos;

import com.aquienllamo.aquienllamo.APIs.Pagos.MetodosPago.ResultadoProcesamientoPago;
import com.aquienllamo.aquienllamo.entidades.pago.dto.request.PagoDTORequest;
import com.aquienllamo.aquienllamo.entidades.pago.entity.PagoEntity;
import com.aquienllamo.aquienllamo.entidades.pago.enums.EProveedorPago;

import java.math.BigDecimal;

public interface ProveedorPago {

    boolean soporta(EProveedorPago proveedor);

    String getNombre();

    ResultadoProcesamientoPago crearCheckout(
            PagoDTORequest request,
            PagoEntity pago,
            BigDecimal monto
    );

    ResultadoConsultaPago consultarEstado(PagoEntity pago);
}
