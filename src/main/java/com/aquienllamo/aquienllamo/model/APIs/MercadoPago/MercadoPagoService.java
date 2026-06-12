package com.aquienllamo.aquienllamo.model.APIs.MercadoPago;

import com.aquienllamo.aquienllamo.model.dtos.Request.PagoDTORequest;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MercadoPagoService {

    private final MercadoPagoProperties properties;

    public Payment procesarTransferencia(PagoDTORequest request) {
        MercadoPagoConfig.setAccessToken(properties.getAccessToken());

        PaymentClient client = new PaymentClient();

        PaymentCreateRequest paymentRequest = PaymentCreateRequest.builder()
                .transactionAmount(request.getMonto())
                .description("Pago trabajo " + request.getUuidTrabajo())
                .paymentMethodId("bank_transfer")
                .payer(
                        PaymentPayerRequest.builder()
                                .email(request.getBuyerEmail())
                                .build()
                )
                .build();

        try {
            return client.create(paymentRequest);
        } catch (MPException | MPApiException e) {
            throw new RuntimeException("Error al procesar transferencia con Mercado Pago", e);
        }
    }
}
