package com.aquienllamo.aquienllamo.APIs.Pagos.MercadoPago.Webhook;


import com.aquienllamo.aquienllamo.entidades.pago.service.PagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@RestController
@RequestMapping("/webhooks/mercado-pago")
@RequiredArgsConstructor
public class MercadoPagoWebhookController {

    private final MercadoPagoWebhookValidator webhookValidator;
    private final PagoService pagoService;

    @PostMapping
    public ResponseEntity<Void> recibirNotificacion(
            @RequestParam Map<String, String> params,
            @RequestHeader(name = "x-signature", required = false) String xSignature,
            @RequestHeader(name = "x-request-id", required = false) String xRequestId
    ) {

        // Formato actual
        String dataId = params.get("data.id");
        String type = params.get("type");

        System.out.println("Data ID: " + dataId);
        System.out.println("Type: " + type);
        System.out.println("Signature presente: " + (xSignature != null));
        System.out.println("Request ID presente: " + (xRequestId != null));

        // Compatibilidad con la URL de prueba
        if (dataId == null) {
            dataId = params.get("id");
        }

        if (type == null) {
            type = params.get("topic");
        }

        if (dataId == null || dataId.isBlank()
                || type == null || type.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        // Validar firma
        if (!webhookValidator.validarFirma(
                xSignature, xRequestId, dataId)) {
            return ResponseEntity.status(401).build();
        }

        try {

            switch (type.toLowerCase()) {

                case "payment":
                    // Procesar notificación de pago
                    pagoService.procesarWebhookMercadoPago(dataId);
                    break;

                case "order":
                    // Procesar notificación de orden
                    pagoService.procesarWebhookMercadoPago(dataId);
                    break;

                default:
                    return ResponseEntity.ok().build();
            }

            return ResponseEntity.ok().build();

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}