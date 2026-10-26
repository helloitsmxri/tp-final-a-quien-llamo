
package com.aquienllamo.aquienllamo.model.APIs.Pagos.MercadoPago;

import com.aquienllamo.aquienllamo.model.APIs.Pagos.ProveedorPago;
import com.aquienllamo.aquienllamo.model.APIs.Pagos.ResultadoConsultaPago;
import com.aquienllamo.aquienllamo.model.Enum.EstadoPago;
import com.aquienllamo.aquienllamo.model.dtos.Request.PagoDTORequest;
import com.aquienllamo.aquienllamo.model.entities.PagoEntity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MercadoPagoService implements ProveedorPago {

    private final MercadoPagoProperties properties;

    @Override
    public String getNombre() {
        return "MERCADO_PAGO";
    }

    public MercadoPagoOrderResponse crearOrdenCheckout(
            PagoDTORequest request,
            PagoEntity pago,
            BigDecimal monto) {

        if (monto == null ||
                monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "El importe debe ser mayor que cero."
            );
        }

        if (pago.getUuid() == null ||
                pago.getTrabajo() == null) {
            throw new IllegalArgumentException(
                    "El pago debe estar guardado y asociado a un trabajo."
            );
        }

        if (request.getBuyerEmail() == null ||
                request.getBuyerEmail().isBlank()) {
            throw new IllegalArgumentException(
                    "El email del comprador es obligatorio."
            );
        }

        String importe = monto
                .setScale(2, RoundingMode.UNNECESSARY)
                .toPlainString();

        Map<String, Object> item = Map.of(
                "title", "Trabajo " + pago.getTrabajo().getUuid(),
                "quantity", 1,
                "unit_price", importe
        );

        Map<String, Object> body = Map.of(
                "type", "online",
                "processing_mode", "manual",
                "total_amount", importe,
                "external_reference", pago.getUuid(),
                "description",
                "Pago de trabajo " + pago.getTrabajo().getUuid(),
                "payer", Map.of(
                        "email", request.getBuyerEmail()
                ),
                "items", List.of(item)
        );

        RestClient client = crearCliente();

        MercadoPagoOrderResponse respuesta = client.post()
                .uri("/v1/orders")
                .header("X-Idempotency-Key", pago.getUuid())
                .body(body)
                .retrieve()
                .body(MercadoPagoOrderResponse.class);

        if (respuesta == null
                || respuesta.getId() == null
                || respuesta.getCheckoutUrl() == null
                || respuesta.getCheckoutUrl().isBlank()) {
            throw new IllegalStateException(
                    "Mercado Pago no devolvió el ID y la URL de checkout."
            );
        }

        return respuesta;
    }

    public MercadoPagoOrderStatusResponse consultarOrden(String orderId) {

        if (orderId == null || orderId.isBlank()) {
            throw new IllegalArgumentException(
                    "El ID de la orden es obligatorio."
            );
        }

        MercadoPagoOrderStatusResponse respuesta = crearCliente()
                .get()
                .uri("/v1/orders/{id}", orderId)
                .retrieve()
                .body(MercadoPagoOrderStatusResponse.class);

        if (respuesta == null || respuesta.getId() == null) {
            throw new IllegalStateException(
                    "Mercado Pago no devolvió información de la orden."
            );
        }

        return respuesta;
    }

    @Override
    public ResultadoConsultaPago consultarEstado(PagoEntity pago) {

        if (pago == null ||
                pago.getMercadoPagoOrderId() == null ||
                pago.getMercadoPagoOrderId().isBlank()) {
            throw new IllegalArgumentException(
                    "El pago no tiene una orden de Mercado Pago asociada."
            );
        }

        MercadoPagoOrderStatusResponse orden =
                consultarOrden(pago.getMercadoPagoOrderId());

        // Verificar que la orden corresponda al pago local.
        if (orden.getExternalReference() == null
                || !orden.getExternalReference().equals(pago.getUuid())) {
            throw new IllegalStateException(
                    "La orden de Mercado Pago no corresponde al pago indicado."
            );
        }

        String estado = orden.getStatus();
        String detalle = orden.getStatusDetail();

        if ("processed".equalsIgnoreCase(estado)
                && "accredited".equalsIgnoreCase(detalle)) {

            return new ResultadoConsultaPago(
                    EstadoPago.Confirmado, true
            );
        }

        if ("canceled".equalsIgnoreCase(estado)) {
            return new ResultadoConsultaPago(
                    EstadoPago.Cancelado, true
            );
        }

        if ("failed".equalsIgnoreCase(estado)) {
            return new ResultadoConsultaPago(
                    EstadoPago.Rechazado, true
            );
        }

        if ("processing".equalsIgnoreCase(estado)
                || "in_process".equalsIgnoreCase(estado)
                || "action_required".equalsIgnoreCase(estado)) {

            return new ResultadoConsultaPago(
                    EstadoPago.Pendiente_de_revision, true
            );
        }

        // Estado desconocido: no modificar el estado local.
        return new ResultadoConsultaPago(
                null, false
        );
    }

    private RestClient crearCliente() {
        return RestClient.builder()
                .baseUrl(properties.getBaseUrl())
                .defaultHeader(
                        "Authorization",
                        "Bearer " + properties.getAccessToken()
                )
                .build();
    }
}
