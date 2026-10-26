
package com.aquienllamo.aquienllamo.model.services;

import com.aquienllamo.aquienllamo.model.APIs.Pagos.MetodosPago.PagoFactory;
import com.aquienllamo.aquienllamo.model.APIs.Pagos.MetodosPago.MetodoPago;
import com.aquienllamo.aquienllamo.model.APIs.Pagos.MetodosPago.ResultadoProcesamientoPago;
import com.aquienllamo.aquienllamo.model.APIs.Pagos.ProveedorPago;
import com.aquienllamo.aquienllamo.model.APIs.Pagos.ResultadoConsultaPago;
import com.aquienllamo.aquienllamo.model.Enum.EstadoPago;
import com.aquienllamo.aquienllamo.model.Enum.EstadoPresupuestoE;
import com.aquienllamo.aquienllamo.model.Enum.MetodoDePago;
import com.aquienllamo.aquienllamo.model.dtos.Request.PagoDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.PagoDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.PagoEntity;
import com.aquienllamo.aquienllamo.model.entities.TrabajoEntity;
import com.aquienllamo.aquienllamo.model.exceptions.*;
import com.aquienllamo.aquienllamo.model.mappers.PagoMapper;
import com.aquienllamo.aquienllamo.model.repositories.PagoRepository;
import com.aquienllamo.aquienllamo.model.repositories.TecnicoRepository;
import com.aquienllamo.aquienllamo.model.repositories.TrabajoRepository;
import com.aquienllamo.aquienllamo.model.repositories.UsuarioRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PagoService {

    private final PagoRepository pagoRepository;
    private final TrabajoRepository trabajoRepository;
    private final UsuarioRepository usuarioRepository;
    private final TecnicoRepository tecnicoRepository;
    private final PagoFactory pagoFactory;

    // Interfaz común para consultar el estado del proveedor.
    private final ProveedorPago proveedorPago;


    // Crear un pago.
    public PagoDTOResponse crearPago(PagoDTORequest request) {

        TrabajoEntity trabajo = trabajoRepository
                .findByUuid(request.getUuidTrabajo())
                .orElseThrow(() ->
                        new TrabajoNotFoundEx("El trabajo ingresado no existe.")
                );

        if (trabajo.getPresupuesto() == null
                || trabajo.getPresupuesto().getEstado() == null) {
            throw new TrabajoNotAcceptedEx("El trabajo no posee un presupuesto o estado válido.");
        }

        if (!EstadoPresupuestoE.Aceptado.equals(
                trabajo.getPresupuesto().getEstado())) {
            throw new TrabajoNotAcceptedEx("El trabajo que intenta pagar no está aprobado.");
        }

        // Obtener el importe oficial desde el presupuesto persistido.
        BigDecimal monto = trabajo.getPresupuesto().getPrecioEstimado();

        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El presupuesto no posee un importe válido.");
        }

        // Crear el pago con el importe calculado en el backend.
        PagoEntity pago = PagoMapper.toEntity(request, trabajo, monto);

        // Obtener la estrategia de procesamiento.
        MetodoPago estrategia =
                pagoFactory.obtener(request.getMetodoDePago());

        // Guardar primero para generar el UUID.
        pago.setEstadoPago(EstadoPago.Pendiente_de_revision);
        pago = pagoRepository.saveAndFlush(pago);

        // Procesar el pago con la estrategia correspondiente.
        ResultadoProcesamientoPago resultado = estrategia.procesar(request, pago, monto);

        // Actualizar el estado devuelto por la estrategia.
        pago.setEstadoPago(resultado.getEstadoPago());

        // Persistir los cambios.
        pago = pagoRepository.save(pago);

        return PagoMapper.toResponse(pago, resultado.getCheckoutUrl());
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

        // Consultar al proveedor mediante la interfaz común.
        ResultadoConsultaPago resultado =
                proveedorPago.consultarEstado(pago);

        // Si el proveedor no pudo verificar el estado, no modificarlo.
        if (resultado == null || !resultado.verificado()) {
            return PagoMapper.toResponse(pago);
        }

        // Actualizar solo si el resultado contiene un estado válido.
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
                .findByMercadoPagoOrderId(orderId)
                .orElseThrow(() -> new PagoNotFoundEx("No existe un pago asociado a esta orden.")
                );
        // Reutiliza la lógica existente de consulta y actualización.
        sincronizarEstado(pago.getUuid());
    }

    // Buscar un pago por UUID.
    public PagoDTOResponse buscarPagoPorUuid(String uuid) {
        PagoEntity pago = pagoRepository.findByUuid(uuid)
                .orElseThrow(() -> new PagoNotFoundEx("El pago ingresado no existe."));
        return PagoMapper.toResponse(pago);
    }

    // Buscar un pago por ID de orden de Mercado Pago.
    public PagoDTOResponse buscarPagoPorMercadoPagoOrderId(String mercadoPagoOrderId) {

        PagoEntity pago = pagoRepository
                .findByMercadoPagoOrderId(mercadoPagoOrderId)
                .orElseThrow(() -> new PagoNotFoundEx("No existe un pago asociado a esta orden de Mercado Pago."));
        return PagoMapper.toResponse(pago);
    }

}
