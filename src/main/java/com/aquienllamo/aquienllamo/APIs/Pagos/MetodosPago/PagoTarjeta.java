package com.aquienllamo.aquienllamo.APIs.Pagos.MetodosPago;

import com.aquienllamo.aquienllamo.APIs.Pagos.MercadoPago.MercadoPagoOrderResponse;
import com.aquienllamo.aquienllamo.APIs.Pagos.MercadoPago.MercadoPagoService;
import com.aquienllamo.aquienllamo.entidades.pago.enums.EstadoPago;
import com.aquienllamo.aquienllamo.entidades.pago.enums.MetodoDePago;
import com.aquienllamo.aquienllamo.entidades.pago.dto.request.PagoDTORequest;
import com.aquienllamo.aquienllamo.entidades.pago.entity.PagoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class PagoTarjeta implements MetodoPago {

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
        return metodo == MetodoDePago.Credito
                || metodo == MetodoDePago.Debito;
    }
}