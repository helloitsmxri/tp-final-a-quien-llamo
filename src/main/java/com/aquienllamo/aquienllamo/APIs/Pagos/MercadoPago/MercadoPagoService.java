
package com.aquienllamo.aquienllamo.APIs.Pagos.MercadoPago;

import com.aquienllamo.aquienllamo.APIs.Pagos.MetodosPago.ResultadoProcesamientoPago;
import com.aquienllamo.aquienllamo.APIs.Pagos.ProveedorPago;
import com.aquienllamo.aquienllamo.APIs.Pagos.ResultadoConsultaPago;
import com.aquienllamo.aquienllamo.entidades.pago.enums.EProveedorPago;
import com.aquienllamo.aquienllamo.entidades.pago.enums.EstadoPago;
import com.aquienllamo.aquienllamo.entidades.pago.dto.request.PagoDTORequest;
import com.aquienllamo.aquienllamo.entidades.pago.entity.PagoEntity;

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

    // Crear una Order en Mercado Pago.
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

        // El importe debe tener dos decimales.
        String importe = monto
                .setScale(2, RoundingMode.UNNECESSARY)
                .toPlainString();

        // Ítem de la Order.
        Map<String, Object> item = Map.of(
                "title", "Trabajo " + pago.getTrabajo().getUuid(),
                "quantity", 1,
                "unit_price", importe
        );

        // Cuerpo de la solicitud.
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

        // Crear la Order.
        MercadoPagoOrderResponse respuesta = crearCliente()
                .post()
                .uri("/v1/orders")
                .header("X-Idempotency-Key", pago.getUuid())
                .body(body)
                .retrieve()
                .body(MercadoPagoOrderResponse.class);

        if (respuesta == null
                || respuesta.getId() == null
                || respuesta.getId().isBlank()
                || respuesta.getCheckoutUrl() == null
                || respuesta.getCheckoutUrl().isBlank()) {

            throw new IllegalStateException(
                    "Mercado Pago no devolvió el ID y la URL de la Order."
            );
        }

        return respuesta;
    }

    // Consultar una Order por su ID.
    public MercadoPagoOrderStatusResponse consultarOrden(String orderId) {

        if (orderId == null || orderId.isBlank()) {

            throw new IllegalArgumentException(
                    "El ID de la Order es obligatorio."
            );
        }

        MercadoPagoOrderStatusResponse respuesta = crearCliente()
                .get()
                .uri("/v1/orders/{id}", orderId)
                .retrieve()
                .body(MercadoPagoOrderStatusResponse.class);

        if (respuesta == null || respuesta.getId() == null) {

            throw new IllegalStateException(
                    "Mercado Pago no devolvió información de la Order."
            );
        }

        return respuesta;
    }

    // Consultar el estado de un pago mediante su Order.
    @Override
    public ResultadoConsultaPago consultarEstado(PagoEntity pago) {

        if (pago == null
                || pago.getDatosProveedor() == null
                || pago.getDatosProveedor().getExternalPaymentId() == null
                || pago.getDatosProveedor().getExternalPaymentId().isBlank()) {

            throw new IllegalArgumentException(
                    "El pago no tiene una Order de Mercado Pago asociada."
            );
        }

        String orderId =
                pago.getDatosProveedor().getExternalPaymentId();

        MercadoPagoOrderStatusResponse orden =
                consultarOrden(orderId);

        // Verificar que la Order pertenezca al pago local.
        if (orden.getExternalReference() == null
                || !orden.getExternalReference().equals(pago.getUuid())) {

            throw new IllegalStateException(
                    "La Order de Mercado Pago no corresponde al pago indicado."
            );
        }

        String estado = orden.getStatus();
        String detalle = orden.getStatusDetail();

        if ("processed".equalsIgnoreCase(estado)
                && "accredited".equalsIgnoreCase(detalle)) {

            return new ResultadoConsultaPago(
                    EstadoPago.Confirmado,
                    true
            );
        }

        if ("canceled".equalsIgnoreCase(estado)) {

            return new ResultadoConsultaPago(
                    EstadoPago.Cancelado,
                    true
            );
        }

        if ("failed".equalsIgnoreCase(estado)) {

            return new ResultadoConsultaPago(
                    EstadoPago.Rechazado,
                    true
            );
        }

        if ("processing".equalsIgnoreCase(estado)
                || "in_process".equalsIgnoreCase(estado)
                || "action_required".equalsIgnoreCase(estado)
                || "created".equalsIgnoreCase(estado)) {

            return new ResultadoConsultaPago(
                    EstadoPago.Pendiente_de_revision,
                    true
            );
        }

        // No modificar estados desconocidos.
        return new ResultadoConsultaPago(
                null,
                false
        );
    }

    // Crear cliente HTTP.
    private RestClient crearCliente() {

        return RestClient.builder()
                .baseUrl(properties.getBaseUrl())
                .defaultHeader(
                        "Authorization",
                        "Bearer " + properties.getAccessToken()
                )
                .defaultHeader(
                        "Content-Type",
                        "application/json"
                )
                .defaultHeader(
                        "Accept",
                        "application/json"
                )
                .build();
    }

    @Override
    public boolean soporta(EProveedorPago proveedor) {
        return proveedor == EProveedorPago.MERCADO_PAGO;
    }

    // Implementación de la estrategia de checkout.
    @Override
    public ResultadoProcesamientoPago crearCheckout(
            PagoDTORequest request,
            PagoEntity pago,
            BigDecimal monto) {

        MercadoPagoOrderResponse respuesta =
                crearOrdenCheckout(request, pago, monto);

        return new ResultadoProcesamientoPago(
                EstadoPago.Pendiente_de_revision,
                respuesta.getCheckoutUrl(),
                respuesta.getId(),
                respuesta.getStatus()
        );
    }
}
