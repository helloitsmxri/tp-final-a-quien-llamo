
package com.aquienllamo.aquienllamo.APIs.Pagos.MercadoPago.Webhook;

import com.aquienllamo.aquienllamo.APIs.Pagos.MercadoPago.MercadoPagoProperties;
import com.mercadopago.webhook.WebhookSignatureValidator;
import com.mercadopago.exceptions.MPInvalidWebhookSignatureException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MercadoPagoWebhookValidator {

    private final MercadoPagoProperties properties;

    public boolean validarFirma(
            String xSignature,
            String xRequestId,
            String dataId
    ) {

        String secret = properties.getWebhookSecret();

        if (secret == null || secret.isBlank()) {
            System.err.println("ERROR: secreto no configurado.");
            return false;
        }

        if (xSignature == null || xSignature.isBlank()
                || xRequestId == null || xRequestId.isBlank()
                || dataId == null || dataId.isBlank()) {

            System.err.println("ERROR: faltan datos para validar.");
            return false;
        }

        try {

            WebhookSignatureValidator.validate(
                    xSignature,
                    xRequestId,
                    dataId,
                    secret
            );

            System.out.println("Firma de Mercado Pago válida.");

            return true;

        } catch (MPInvalidWebhookSignatureException e) {

            System.err.println("Firma de Mercado Pago inválida.");

            return false;
        }
    }
}
