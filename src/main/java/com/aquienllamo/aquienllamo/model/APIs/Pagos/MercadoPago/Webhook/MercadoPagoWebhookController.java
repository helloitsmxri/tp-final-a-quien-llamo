package com.aquienllamo.aquienllamo.model.APIs.Pagos.MercadoPago.Webhook;


import com.aquienllamo.aquienllamo.model.services.PagoService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/webhooks/mercado-pago")
@RequiredArgsConstructor
public class MercadoPagoWebhookController {

    private final MercadoPagoWebhookValidator webhookValidator;
    private final PagoService pagoService;

    @PostMapping
    public ResponseEntity<Void> recibirNotificacion(
            @RequestParam(name = "data.id", required = false)
            String dataId,

            @RequestParam(name = "type", required = false)
            String type,

            @RequestHeader(
                    name = "x-signature",
                    required = false
            )
            String xSignature,

            @RequestHeader(
                    name = "x-request-id",
                    required = false
            )
            String xRequestId,

            @RequestBody(required = false)
            Map<String, Object> body) {

        if (!"order".equalsIgnoreCase(type) || dataId == null) {
            return ResponseEntity.badRequest().build();
        }

        boolean firmaValida = webhookValidator.validarFirma(
                xSignature,
                xRequestId,
                dataId
        );

        if (!firmaValida) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        try {
            pagoService.procesarWebhookMercadoPago(dataId);
            return ResponseEntity.ok().build();

        } catch (com.aquienllamo.aquienllamo.model.exceptions.PagoNotFoundEx e) {
            // La orden puede no estar registrada localmente.
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}