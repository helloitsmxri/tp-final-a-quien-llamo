package com.aquienllamo.aquienllamo.model.APIs.Pagos.MetodosPago;

import com.aquienllamo.aquienllamo.model.APIs.Pagos.MercadoPago.MercadoPagoOrderResponse;
import com.aquienllamo.aquienllamo.model.APIs.Pagos.MercadoPago.MercadoPagoService;
import com.aquienllamo.aquienllamo.model.Enum.EstadoPago;
import com.aquienllamo.aquienllamo.model.Enum.MetodoDePago;
import com.aquienllamo.aquienllamo.model.dtos.Request.PagoDTORequest;
import com.aquienllamo.aquienllamo.model.entities.PagoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class PagoTransferencia implements MetodoPago {

    private final MercadoPagoService mercadoPagoService;

    @Override
    public ResultadoProcesamientoPago procesar(
            PagoDTORequest request,
            PagoEntity pago,
            BigDecimal monto) {

        MercadoPagoOrderResponse respuesta =
                mercadoPagoService.crearOrdenCheckout(
                        request,
                        pago,
                        monto
                );

        pago.setMercadoPagoOrderId(respuesta.getId());

        return new ResultadoProcesamientoPago(
                EstadoPago.Pendiente_de_revision,
                respuesta.getCheckoutUrl()
        );
    }

    @Override
    public boolean soporta(MetodoDePago metodo) {
        return metodo == MetodoDePago.Transferencia;
    }
}
