package com.aquienllamo.aquienllamo.APIs.Pagos.MercadoPago.Webhook;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MercadoPagoWebhookValidator {

    public boolean validarFirma(
            String xSignature,
            String xRequestId,
            String dataId
    ) {

        log.warn("Validación de firma de Mercado Pago deshabilitada.");

        return true;
    }
}