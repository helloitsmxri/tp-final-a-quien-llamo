package com.aquienllamo.aquienllamo.model.APIs.Pagos;

import com.aquienllamo.aquienllamo.model.entities.PagoEntity;

public interface ProveedorPago {


    String getNombre();

    ResultadoConsultaPago consultarEstado(PagoEntity pago);
}
