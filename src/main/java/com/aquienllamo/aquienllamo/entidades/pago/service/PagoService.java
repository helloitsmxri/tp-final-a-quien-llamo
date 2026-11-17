
package com.aquienllamo.aquienllamo.entidades.pago.service;

import com.aquienllamo.aquienllamo.APIs.Pagos.MetodosPago.MetodoPagoFactory;
import com.aquienllamo.aquienllamo.APIs.Pagos.MetodosPago.MetodoPago;
import com.aquienllamo.aquienllamo.APIs.Pagos.MetodosPago.ResultadoProcesamientoPago;
import com.aquienllamo.aquienllamo.APIs.Pagos.ProveedorPago;
import com.aquienllamo.aquienllamo.APIs.Pagos.ProveedorPagoFactory;
import com.aquienllamo.aquienllamo.APIs.Pagos.ResultadoConsultaPago;
import com.aquienllamo.aquienllamo.entidades.pago.entity.PagoProveedorEntity;
import com.aquienllamo.aquienllamo.entidades.pago.enums.EstadoPago;
import com.aquienllamo.aquienllamo.entidades.pago.enums.MetodoDePago;
import com.aquienllamo.aquienllamo.entidades.pago.dto.request.PagoDTORequest;
import com.aquienllamo.aquienllamo.entidades.pago.dto.response.PagoDTOResponse;
import com.aquienllamo.aquienllamo.entidades.pago.entity.PagoEntity;
import com.aquienllamo.aquienllamo.entidades.pago.exceptions.PagoNotFoundEx;
import com.aquienllamo.aquienllamo.entidades.pago.exceptions.PaymentCannotBeCancelledException;
import com.aquienllamo.aquienllamo.entidades.pago.mapper.PagoMapper;
import com.aquienllamo.aquienllamo.entidades.pago.repository.PagoRepository;
import com.aquienllamo.aquienllamo.entidades.presupuesto.enums.EstadoPresupuestoE;
import com.aquienllamo.aquienllamo.entidades.tecnico.exceptions.TecnicoNotFoundEx;
import com.aquienllamo.aquienllamo.entidades.trabajo.entity.TrabajoEntity;
import com.aquienllamo.aquienllamo.entidades.tecnico.repository.TecnicoRepository;
import com.aquienllamo.aquienllamo.entidades.trabajo.repository.TrabajoRepository;
import com.aquienllamo.aquienllamo.entidades.trabajo.exceptions.TrabajoNotAcceptedEx;
import com.aquienllamo.aquienllamo.entidades.trabajo.exceptions.TrabajoNotFoundEx;
import com.aquienllamo.aquienllamo.entidades.usuario.repository.UsuarioRepository;

import com.aquienllamo.aquienllamo.entidades.usuario.exceptions.UserNotFoundEx;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PagoService {

    private final PagoRepository pagoRepository;
    private final TrabajoRepository trabajoRepository;
    private final UsuarioRepository usuarioRepository;
    private final TecnicoRepository tecnicoRepository;
    private final MetodoPagoFactory pagoFactory;
    private final ProveedorPagoFactory proveedorPagoFactory;


    // Crear un pago.
    // Crear un pago.

    public PagoDTOResponse crearPago(PagoDTORequest request) {

        // 1. Buscar el trabajo.
        TrabajoEntity trabajo = trabajoRepository
                .findByUuid(request.getUuidTrabajo())
                .orElseThrow(() ->
                        new TrabajoNotFoundEx("El trabajo ingresado no existe.")
                );

        // 2. Verificar si el trabajo ya tiene un pago registrado.
        if (pagoRepository.existsByTrabajo_Uuid(trabajo.getUuid())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este trabajo ya tiene un pago registrado.");
        }

        // 3. Verificar que tenga un presupuesto válido.
        if (trabajo.getPresupuesto() == null
                || trabajo.getPresupuesto().getEstado() == null) {

            throw new TrabajoNotAcceptedEx(
                    "El trabajo no posee un presupuesto o estado válido."
            );
        }

        // 4. Verificar que el presupuesto esté aceptado.
        if (!EstadoPresupuestoE.Aceptado.equals(
                trabajo.getPresupuesto().getEstado())) {

            throw new TrabajoNotAcceptedEx(
                    "El trabajo que intenta pagar no está aprobado."
            );
        }

        // 5. Obtener el importe oficial.
        BigDecimal monto = trabajo.getPresupuesto().getPrecioEstimado();

        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "El presupuesto no posee un importe válido."
            );
        }

        // 6. Obtener el proveedor.
        ProveedorPago proveedor =
                proveedorPagoFactory.obtener(request.getProveedorDePago());

        // 7. Obtener la estrategia.
        MetodoPago estrategia =
                pagoFactory.obtener(request.getMetodoDePago());

        // 8. Crear el pago.
        PagoEntity pago = PagoMapper.toEntity(request, trabajo, monto);

        pago.setEstadoPago(EstadoPago.Pendiente_de_revision);

        // 9. Guardar el pago para generar su UUID.
        pago = pagoRepository.saveAndFlush(pago);

        // 10. Procesar el pago con la pasarela.
        ResultadoProcesamientoPago resultado =
                estrategia.procesar(
                        request,
                        pago,
                        monto,
                        proveedor
                );

        if (resultado == null) {
            throw new IllegalStateException(
                    "La pasarela no devolvió un resultado del procesamiento."
            );
        }

        // 11. Actualizar el estado interno del pago.
        if (resultado.getEstadoPago() != null) {
            pago.setEstadoPago(resultado.getEstadoPago());
        }

        // 12. Crear los datos específicos del proveedor.
        PagoProveedorEntity datosProveedor = PagoProveedorEntity.builder()
                .pago(pago)
                .proveedor(request.getProveedorDePago())
                .externalPaymentId(resultado.getExternalPaymentId())
                .checkoutUrl(resultado.getCheckoutUrl())
                .estadoExterno(resultado.getEstadoExterno())
                .fechaActualizacion(LocalDateTime.now())
                .build();

        // 13. Asociar los datos del proveedor al pago.
        pago.setDatosProveedor(datosProveedor);

        // 14. Guardar el pago y sus datos del proveedor por cascada.
        pago = pagoRepository.save(pago);

        // 15. Devolver el DTO.
        return PagoMapper.toResponse(pago);
    }


    // Sincronizar el estado de un pago con su proveedor externo.
    public PagoDTOResponse sincronizarEstado(String uuidPago) {

        PagoEntity pago = pagoRepository.findByUuid(uuidPago)
                .orElseThrow(() ->
                        new PagoNotFoundEx("El pago ingresado no existe.")
                );

        // No modificar estados definitivos.
        if (pago.getEstadoPago() == EstadoPago.Confirmado
                || pago.getEstadoPago() == EstadoPago.Cancelado
                || pago.getEstadoPago() == EstadoPago.Rechazado) {

            return PagoMapper.toResponse(pago);
        }

        // Obtener el proveedor correspondiente al pago.
        ProveedorPago proveedor =
                proveedorPagoFactory.obtener(pago.getProveedorDePago());

        // Consultar el estado.
        ResultadoConsultaPago resultado =
                proveedor.consultarEstado(pago);

        if (resultado == null || !resultado.verificado()) {
            return PagoMapper.toResponse(pago);
        }

        if (resultado.estadoPago() != null) {
            pago.setEstadoPago(resultado.estadoPago());
            pago = pagoRepository.save(pago);
        }

        return PagoMapper.toResponse(pago);
    }


    // Listar pagos según un método de pago.
    public List<PagoDTOResponse> listarPorMetodoDePago(
            MetodoDePago metodoDePago) {

        return pagoRepository.findAllByMetodoDePago(metodoDePago)
                .stream()
                .map(PagoMapper::toResponse)
                .toList();
    }


    // Listar pagos según un estado.
    public List<PagoDTOResponse> listarPorEstado(EstadoPago estado) {

        return pagoRepository.findAllByEstadoPago(estado)
                .stream()
                .map(PagoMapper::toResponse)
                .toList();
    }


    // Listar todos los pagos.
    public List<PagoDTOResponse> listarPagos() {

        return pagoRepository.findAll()
                .stream()
                .map(PagoMapper::toResponse)
                .toList();
    }


    // Listar todos los pagos hechos por un cliente.
    public List<PagoDTOResponse> listarPagosPorClienteUuid(String uuid) {

        usuarioRepository.findByUuid(uuid)
                .orElseThrow(() ->
                        new UserNotFoundEx("El usuario ingresado no existe.")
                );

        return pagoRepository.findAllByCliente(uuid)
                .stream()
                .map(PagoMapper::toResponse)
                .toList();
    }


    // Listar pagos recibidos por un técnico.
    public List<PagoDTOResponse> listarPagosRecibidosPorTecnico(
            String uuid) {

        tecnicoRepository.findByUuid(uuid)
                .orElseThrow(() ->
                        new TecnicoNotFoundEx("El técnico ingresado no existe.")
                );

        return pagoRepository.findAllByTecnico(uuid)
                .stream()
                .map(PagoMapper::toResponse)
                .toList();
    }


    // Buscar un pago por UUID.
    public PagoDTOResponse buscarPagoPorUuidParaTecnico(String uuid) {

        PagoEntity pago = pagoRepository.findByUuid(uuid)
                .orElseThrow(() ->
                        new PagoNotFoundEx("El pago ingresado no existe.")
                );

        return PagoMapper.toResponse(pago);
    }


    // Cancelar un pago.
    public PagoDTOResponse cancelarPago(String uuid) {

        PagoEntity pago = pagoRepository.findByUuid(uuid)
                .orElseThrow(() -> new PagoNotFoundEx("El pago ingresado no existe.")
                );

        if (!pago.getEstadoPago().equals(
                EstadoPago.Pendiente_de_revision)) {
            throw new PaymentCannotBeCancelledException("Solo se pueden cancelar pagos pendientes.");
        }

        pago.setEstadoPago(EstadoPago.Cancelado);

        return PagoMapper.toResponse(
                pagoRepository.save(pago)
        );
    }


    // Verificar que el usuario de la URL sea el usuario autenticado.
    public boolean perteneceAlUsuario(String uuid, String email) {
        return usuarioRepository.findByUuid(uuid)
                .map(usuario -> usuario.getEmail().equals(email))
                .orElse(false);
    }


    // Verificar que el pago pertenezca al usuario autenticado.
    public boolean pagoPerteneceAlUsuario(String uuid, String email) {

        return pagoRepository.findByUuid(uuid)
                .map(pago ->
                        pago.getTrabajo()
                                .getPresupuesto()
                                .getUsuario()
                                .getEmail()
                                .equals(email))
                .orElse(false);
    }

    public void procesarWebhookMercadoPago(String orderId) {

        PagoEntity pago = pagoRepository
                .findByDatosProveedor_ExternalPaymentId(orderId)
                .orElseThrow(() ->
                        new PagoNotFoundEx(
                                "No se encontró un pago asociado a la orden de Mercado Pago."
                        )
                );

        sincronizarEstado(pago.getUuid());
    }

    // Buscar un pago por UUID.
    public PagoDTOResponse buscarPagoPorUuid(String uuid) {
        PagoEntity pago = pagoRepository.findByUuid(uuid)
                .orElseThrow(() -> new PagoNotFoundEx("El pago ingresado no existe."));
        return PagoMapper.toResponse(pago);
    }

    // Buscar un pago por ID de orden de Mercado Pago.
    public PagoDTOResponse buscarPagoPorMercadoPagoOrderId(String orderId) {

        PagoEntity pago = pagoRepository
                .findByDatosProveedor_ExternalPaymentId(orderId)
                .orElseThrow(() ->
                        new PagoNotFoundEx(
                                "No se encontró un pago asociado a la orden de Mercado Pago."
                        )
                );

        return PagoMapper.toResponse(pago);
    }

}
