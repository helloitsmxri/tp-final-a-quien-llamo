package com.aquienllamo.aquienllamo.APIs.Pagos;

import com.aquienllamo.aquienllamo.entidades.pago.entity.PagoEntity;

public interface ProveedorPago {


    String getNombre();

    ResultadoConsultaPago consultarEstado(PagoEntity pago);
}
