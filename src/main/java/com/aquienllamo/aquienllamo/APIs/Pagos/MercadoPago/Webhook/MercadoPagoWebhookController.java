
package com.aquienllamo.aquienllamo.APIs.Pagos.MercadoPago.Webhook;

import com.aquienllamo.aquienllamo.entidades.pago.service.PagoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhooks/mercado-pago")
@RequiredArgsConstructor
public class MercadoPagoWebhookController {

    private final MercadoPagoWebhookValidator webhookValidator;
    private final PagoService pagoService;

    @PostMapping
    public ResponseEntity<Void> recibirNotificacion(
            HttpServletRequest request
    ) {

        System.out.println("========== WEBHOOK MERCADO PAGO ==========");

        // Obtener los parámetros directamente de la petición HTTP.
        String dataId = request.getParameter("data.id");
        String type = request.getParameter("type");

        String xSignature = request.getHeader("x-signature");
        String xRequestId = request.getHeader("x-request-id");

        System.out.println("Data ID recibido: " + dataId);
        System.out.println("Longitud Data ID: " +
                (dataId == null ? 0 : dataId.length()));

        System.out.println("Tipo: " + type);

        System.out.println("X-Request-Id presente: " +
                (xRequestId != null && !xRequestId.isBlank()));

        System.out.println("X-Signature presente: " +
                (xSignature != null && !xSignature.isBlank()));

        if (dataId == null || dataId.isBlank()
                || type == null || type.isBlank()) {

            System.out.println("ERROR: faltan parámetros obligatorios.");

            return ResponseEntity.badRequest().build();
        }

        if (!"order".equalsIgnoreCase(type)) {
            System.out.println("Notificación ignorada: " + type);
            return ResponseEntity.ok().build();
        }

        System.out.println("Validando firma...");

        boolean firmaValida = webhookValidator.validarFirma(
                xSignature,
                xRequestId,
                dataId
        );

        System.out.println("Resultado validación: " + firmaValida);

        if (!firmaValida) {
            System.out.println("ERROR: firma inválida.");
            return ResponseEntity.status(401).build();
        }

        System.out.println("Firma válida. Procesando Order...");

        try {

            pagoService.procesarWebhookMercadoPago(dataId);

            System.out.println("Webhook procesado correctamente.");

            return ResponseEntity.ok().build();

        } catch (Exception e) {

            System.err.println("ERROR procesando webhook: " + e.getMessage());
            e.printStackTrace();

            return ResponseEntity.internalServerError().build();
        }
    }
}

