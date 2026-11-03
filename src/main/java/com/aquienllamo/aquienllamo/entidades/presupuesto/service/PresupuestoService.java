
package com.aquienllamo.aquienllamo.entidades.presupuesto.service;

import com.aquienllamo.aquienllamo.APIs.GoogleGmail.EmailService;
import com.aquienllamo.aquienllamo.entidades.chat.entity.ChatEntity;
import com.aquienllamo.aquienllamo.entidades.chat.repository.ChatRepository;
import com.aquienllamo.aquienllamo.entidades.mensaje.entity.MensajeEntity;
import com.aquienllamo.aquienllamo.entidades.mensaje.repository.MensajeRepository;
import com.aquienllamo.aquienllamo.entidades.presupuesto.dto.request.PresupuestoDTORequest;
import com.aquienllamo.aquienllamo.entidades.presupuesto.dto.request.PresupuestoDetalleDTORequest;
import com.aquienllamo.aquienllamo.entidades.presupuesto.dto.response.PresupuestoDTOResponse;
import com.aquienllamo.aquienllamo.entidades.presupuesto.entity.PresupuestoDetalleEntity;
import com.aquienllamo.aquienllamo.entidades.presupuesto.entity.PresupuestoEntity;
import com.aquienllamo.aquienllamo.entidades.presupuesto.enums.EstadoPresupuestoE;
import com.aquienllamo.aquienllamo.entidades.presupuesto.enums.TipoPresupuestoE;
import com.aquienllamo.aquienllamo.entidades.presupuesto.exceptions.PresupuestoNotFoundEx;
import com.aquienllamo.aquienllamo.entidades.presupuesto.mapper.PresupuestoMapper;
import com.aquienllamo.aquienllamo.entidades.presupuesto.repository.PresupuestoRepository;
import com.aquienllamo.aquienllamo.entidades.presupuesto.specs.PresupuestoSpecifications;
import com.aquienllamo.aquienllamo.entidades.tecnico.entity.TecnicoEntity;
import com.aquienllamo.aquienllamo.entidades.tecnico.exceptions.TecnicoNotFoundEx;
import com.aquienllamo.aquienllamo.entidades.tecnico.repository.TecnicoRepository;
import com.aquienllamo.aquienllamo.entidades.usuario.entities.UsuarioEntity;
import com.aquienllamo.aquienllamo.entidades.usuario.exceptions.UserNotFoundEx;
import com.aquienllamo.aquienllamo.entidades.usuario.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.data.jpa.domain.PredicateSpecification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PresupuestoService {

    private final PresupuestoMapper presupuestoMapper;
    private final PresupuestoRepository presupuestoRepository;
    private final UsuarioRepository usuarioRepository;
    private final TecnicoRepository tecnicoRepository;
    private final MensajeRepository mensajeRepository;
    private final ChatRepository chatRepository;
    private final EmailService emailService;


    // CREAR PRESUPUESTO

    public PresupuestoDTOResponse createPresupuesto(
            PresupuestoDTORequest dto,
            String uuidChat,
            String emailTech) {

        // 1. Buscar el chat.
        ChatEntity chat = chatRepository.findByUuidChat(uuidChat)
                .orElseThrow(() -> new RuntimeException("No se encontró el chat."));

        TecnicoEntity tecnico = chat.getTecnico();
        UsuarioEntity usuario = chat.getUsuario();

        // 2. Verificar que el técnico autenticado sea el dueño del chat.
        if (!tecnico.getUsuario().getEmail().equals(emailTech)) {
            throw new RuntimeException("No tiene permiso para crear un presupuesto en este chat.");
        }

        // 3. Crear el presupuesto.
        PresupuestoEntity presupuesto = PresupuestoEntity.builder()
                .chat(chat)
                .usuario(usuario)
                .tecnico(tecnico)
                .estado(EstadoPresupuestoE.Pendiente)
                .detalles(new ArrayList<>())
                .build();

        // 4. Cargar los datos y calcular los importes.
        actualizarDatosPresupuesto(presupuesto, dto);

        // 5. Guardar el presupuesto y sus detalles.
        PresupuestoEntity presupuestoGuardado = presupuestoRepository.save(presupuesto);

        // 6. Crear un mensaje automático en el chat.
        MensajeEntity mensaje = new MensajeEntity();
        mensaje.setChat(chat);
        mensaje.setSender(tecnico.getUsuario());
        mensaje.setMensaje(
                "¡Hola! Creé un presupuesto para el proyecto: "
                        + presupuestoGuardado.getNombreProyecto()
                        + ". El precio final es de $"
                        + presupuestoGuardado.getPrecioEstimado()
                        + ". Podés consultar todos los detalles del presupuesto desde acá."
        );

        mensajeRepository.save(mensaje);

        // 7. Notificar al cliente por correo.
        emailService.enviarPresupuestoRecibido(
                usuario.getEmail(),
                usuario.getNombre()
        );

        return presupuestoMapper.toResponse(presupuestoGuardado);
    }


    // RECHAZAR PRESUPUESTO

    public void rechazarPresupuesto(String uuid, String emailUser) {

        PresupuestoEntity presupuesto = buscarPresupuesto(uuid);

        // Verificar que el usuario sea el dueño.
        if (!presupuesto.getUsuario().getEmail().equals(emailUser)) {
            throw new RuntimeException("No tiene permiso para rechazar este presupuesto.");
        }

        // Solo se pueden rechazar presupuestos pendientes.
        verificarEstadoPendiente(presupuesto);

        presupuesto.setEstado(EstadoPresupuestoE.Rechazado);

        presupuestoRepository.save(presupuesto);

        // Crear mensaje automático en el chat.
        MensajeEntity mensaje = new MensajeEntity();
        mensaje.setChat(presupuesto.getChat());
        mensaje.setSender(presupuesto.getUsuario());
        mensaje.setMensaje(
                "Por el momento no voy a continuar con este presupuesto. "
                        + "Gracias por el tiempo que te tomaste en elaborarlo. "
                        + "ATENCIÓN: Este es un mensaje automatizado."
        );

        mensajeRepository.save(mensaje);
    }


    // ACEPTAR PRESUPUESTO

    public void aceptarPresupuesto(String uuid, String emailUser) {

        PresupuestoEntity presupuesto = buscarPresupuesto(uuid);

        // Verificar que el usuario sea el dueño.
        if (!presupuesto.getUsuario().getEmail().equals(emailUser)) {
            throw new RuntimeException("No tiene permiso para aceptar este presupuesto.");
        }

        // Solo se pueden aceptar presupuestos pendientes.
        verificarEstadoPendiente(presupuesto);

        presupuesto.setEstado(EstadoPresupuestoE.Aceptado);

        presupuestoRepository.save(presupuesto);

        // Notificar al técnico.
        emailService.enviarPresupuestoAceptado(
                presupuesto.getTecnico().getUsuario().getEmail()
        );

        // Crear mensaje automático en el chat.
        MensajeEntity mensaje = new MensajeEntity();
        mensaje.setChat(presupuesto.getChat());
        mensaje.setSender(presupuesto.getUsuario());
        mensaje.setMensaje(
                "¡Encantad@ de trabajar con vos! Arreglemos un horario y una fecha. "
                        + "ATENCIÓN: Este es un mensaje automatizado."
        );

        mensajeRepository.save(mensaje);
    }


    // CANCELAR PRESUPUESTO (TÉCNICO)

    public void cancelarPresupuesto(String uuid, String emailTech) {

        PresupuestoEntity presupuesto = buscarPresupuesto(uuid);

        // Verificar que el técnico sea quien creó el presupuesto.
        if (!presupuesto.getTecnico().getUsuario().getEmail().equals(emailTech)) {
            throw new RuntimeException("No tiene permiso para cancelar este presupuesto.");
        }

        // Solo se pueden cancelar presupuestos pendientes.
        verificarEstadoPendiente(presupuesto);

        presupuesto.setEstado(EstadoPresupuestoE.Cancelado);

        presupuestoRepository.save(presupuesto);

        // Notificar al cliente.
        emailService.enviarPresupuestoRechazado(
                presupuesto.getUsuario().getEmail()
        );
    }


    // OBTENER TODOS LOS PRESUPUESTOS (ADMINISTRADOR)

    @Transactional(readOnly = true)
    public List<PresupuestoDTOResponse> getAllPresupuestos() {

        return presupuestoRepository.findAll()
                .stream()
                .map(presupuestoMapper::toResponse)
                .toList();
    }


    // BUSCAR PRESUPUESTOS CON FILTROS (ADMINISTRADOR)

    @Transactional(readOnly = true)
    public List<PresupuestoDTOResponse> buscarPresupuestosCompleto(
            String nombre,
            String apellido,
            BigDecimal min,
            BigDecimal max,
            LocalDate fecha) {

        PredicateSpecification<PresupuestoEntity> spec =
                PredicateSpecification.allOf(
                        PresupuestoSpecifications.presupuestoDeTalPersonaNombre(nombre),
                        PresupuestoSpecifications.presupuestoDeTalPersonaApellido(apellido),
                        PresupuestoSpecifications.presupuestoGreaterThan(min),
                        PresupuestoSpecifications.presupuestoLesserThan(max),
                        PresupuestoSpecifications.presupuestoDeTalFecha(fecha)
                );

        return presupuestoRepository.findAll(spec)
                .stream()
                .map(presupuestoMapper::toResponse)
                .toList();
    }


    // OBTENER UN PRESUPUESTO POR UUID

    @Transactional(readOnly = true)
    public PresupuestoDTOResponse obtenerPorUuid(String uuidPresupuesto, String email) {

        PresupuestoEntity presupuesto = buscarPresupuesto(uuidPresupuesto);

        boolean esUsuario = presupuesto.getUsuario()
                .getEmail()
                .equals(email);

        boolean esTecnico = presupuesto.getTecnico()
                .getUsuario()
                .getEmail()
                .equals(email);

        if (!esUsuario && !esTecnico) {
            throw new RuntimeException("No tiene permiso para consultar este presupuesto.");
        }

        return presupuestoMapper.toResponse(presupuesto);
    }


    // OBTENER PRESUPUESTOS DE UN CLIENTE

    @Transactional(readOnly = true)
    public List<PresupuestoDTOResponse> getPresupuestosUsuario(String emailUser) {

        UsuarioEntity usuario = usuarioRepository.findByEmail(emailUser)
                .orElseThrow(() -> new UserNotFoundEx("No se encontró el usuario."));

        return presupuestoRepository.findByUsuario(usuario)
                .stream()
                .map(presupuestoMapper::toResponse)
                .toList();
    }


    // OBTENER PRESUPUESTOS DE UN TÉCNICO

    @Transactional(readOnly = true)
    public List<PresupuestoDTOResponse> getPresupuestosTecnico(String emailTech) {

        TecnicoEntity tecnico = tecnicoRepository.findByUsuarioEmail(emailTech)
                .orElseThrow(() -> new TecnicoNotFoundEx("No se encontró el técnico."));

        return presupuestoRepository.findByTecnico(tecnico)
                .stream()
                .map(presupuestoMapper::toResponse)
                .toList();
    }


    // OBTENER PRESUPUESTOS DE UN CLIENTE SEGÚN SU ESTADO

    @Transactional(readOnly = true)
    public List<PresupuestoDTOResponse> getPresupuestosUsuarioPorEstado(
            String emailUser,
            EstadoPresupuestoE estado) {

        UsuarioEntity usuario = usuarioRepository.findByEmail(emailUser)
                .orElseThrow(() -> new UserNotFoundEx("No se encontró el usuario."));

        return presupuestoRepository.findByUsuarioAndEstado(usuario, estado)
                .stream()
                .map(presupuestoMapper::toResponse)
                .toList();
    }


    // OBTENER PRESUPUESTOS DE UN TÉCNICO SEGÚN SU ESTADO

    @Transactional(readOnly = true)
    public List<PresupuestoDTOResponse> getPresupuestosTecnicoPorEstado(
            String emailTech,
            EstadoPresupuestoE estado) {

        TecnicoEntity tecnico = tecnicoRepository.findByUsuarioEmail(emailTech)
                .orElseThrow(() -> new TecnicoNotFoundEx("No se encontró el técnico."));

        return presupuestoRepository.findByTecnicoAndEstado(tecnico, estado)
                .stream()
                .map(presupuestoMapper::toResponse)
                .toList();
    }


    // BUSCAR PRESUPUESTOS DE UN CLIENTE CON FILTROS

    @Transactional(readOnly = true)
    public List<PresupuestoDTOResponse> buscarMisPresupuestos(
            String emailUser,
            EstadoPresupuestoE estado,
            BigDecimal min,
            BigDecimal max,
            LocalDate fecha) {

        UsuarioEntity usuario = usuarioRepository.findByEmail(emailUser)
                .orElseThrow(() -> new UserNotFoundEx("No se encontró el usuario."));

        PredicateSpecification<PresupuestoEntity> spec =
                PredicateSpecification.allOf(
                        PresupuestoSpecifications.presupuestoDeUsuario(usuario),
                        PresupuestoSpecifications.presupuestoConEstado(estado),
                        PresupuestoSpecifications.presupuestoGreaterThan(min),
                        PresupuestoSpecifications.presupuestoLesserThan(max),
                        PresupuestoSpecifications.presupuestoDeTalFecha(fecha)
                );

        return presupuestoRepository.findAll(spec)
                .stream()
                .map(presupuestoMapper::toResponse)
                .toList();
    }


    // BUSCAR PRESUPUESTOS DE UN TÉCNICO CON FILTROS

    @Transactional(readOnly = true)
    public List<PresupuestoDTOResponse> buscarMisPresupuestosTecnico(
            String emailTech,
            EstadoPresupuestoE estado,
            BigDecimal min,
            BigDecimal max,
            LocalDate fecha) {

        TecnicoEntity tecnico = tecnicoRepository.findByUsuarioEmail(emailTech)
                .orElseThrow(() -> new TecnicoNotFoundEx("No se encontró el técnico."));

        PredicateSpecification<PresupuestoEntity> spec =
                PredicateSpecification.allOf(
                        PresupuestoSpecifications.presupuestoDeTecnico(tecnico),
                        PresupuestoSpecifications.presupuestoConEstado(estado),
                        PresupuestoSpecifications.presupuestoGreaterThan(min),
                        PresupuestoSpecifications.presupuestoLesserThan(max),
                        PresupuestoSpecifications.presupuestoDeTalFecha(fecha)
                );

        return presupuestoRepository.findAll(spec)
                .stream()
                .map(presupuestoMapper::toResponse)
                .toList();
    }


    // MODIFICAR PRESUPUESTO

    public void modificarPresupuesto(
            String uuid,
            PresupuestoDTORequest dto,
            String emailTech) {

        PresupuestoEntity presupuesto = buscarPresupuesto(uuid);

        // Verificar que el técnico sea quien lo creó.
        if (!presupuesto.getTecnico().getUsuario().getEmail().equals(emailTech)) {
            throw new RuntimeException("No tiene permiso para modificar este presupuesto.");
        }

        // Solo se pueden modificar presupuestos pendientes.
        verificarEstadoPendiente(presupuesto);

        // Actualizar los datos y recalcular los importes.
        actualizarDatosPresupuesto(presupuesto, dto);

        presupuestoRepository.save(presupuesto);
    }


    // OBTENER PRESUPUESTOS DE UN CHAT

    @Transactional(readOnly = true)
    public List<PresupuestoDTOResponse> obtenerPresupuestosChat(
            String uuidChat,
            String email) {

        ChatEntity chat = chatRepository.findByUuidChat(uuidChat)
                .orElseThrow(() -> new RuntimeException("No se encontró el chat."));

        // Verificar que el usuario autenticado participe del chat.
        boolean esUsuario = chat.getUsuario().getEmail().equals(email);

        boolean esTecnico = chat.getTecnico()
                .getUsuario()
                .getEmail()
                .equals(email);

        if (!esUsuario && !esTecnico) {
            throw new RuntimeException("No tiene permiso para consultar los presupuestos de este chat.");
        }

        return presupuestoRepository.findByChat_UuidChat(uuidChat)
                .stream()
                .map(presupuestoMapper::toResponse)
                .toList();
    }


    // OBTENER PRESUPUESTOS CREADOS O RECIBIDOS

    @Transactional(readOnly = true)
    public List<PresupuestoDTOResponse> obtenerMisPresupuestos(
            String email,
            TipoPresupuestoE tipo) {

        if (tipo == null) {
            throw new IllegalArgumentException("Debe indicar el tipo de presupuesto.");
        }

        List<PresupuestoEntity> presupuestos;

        if (tipo == TipoPresupuestoE.CREADOS) {
            presupuestos = presupuestoRepository.findByTecnico_Usuario_Email(email);
        } else {
            presupuestos = presupuestoRepository.findByUsuario_Email(email);
        }

        return presupuestos.stream()
                .map(presupuestoMapper::toResponse)
                .toList();
    }


    // MÉTODOS AUXILIARES PRIVADOS


    // Busca un presupuesto o lanza una excepción si no existe.
    private PresupuestoEntity buscarPresupuesto(String uuid) {

        return presupuestoRepository.findByUuid(uuid)
                .orElseThrow(() ->
                        new PresupuestoNotFoundEx("No se encontró el presupuesto."));
    }


    // Verifica que el presupuesto esté pendiente.
    private void verificarEstadoPendiente(PresupuestoEntity presupuesto) {

        if (presupuesto.getEstado() != EstadoPresupuestoE.Pendiente) {
            throw new RuntimeException("Esta operación solo está permitida para presupuestos pendientes.");
        }
    }


    // Carga los datos del presupuesto y calcula todos sus importes.
    private void actualizarDatosPresupuesto(
            PresupuestoEntity presupuesto,
            PresupuestoDTORequest dto) {

        // 1. Calcular el subtotal.
        BigDecimal subtotal = dto.getDetalles()
                .stream()
                .map(detalle -> detalle.getPrecioUnitario()
                        .multiply(BigDecimal.valueOf(detalle.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        subtotal = subtotal.setScale(2, RoundingMode.HALF_UP);

        // 2. Normalizar el porcentaje de descuento.
        BigDecimal descuentoPorcentaje = dto.getDescuentoPorcentaje()
                .setScale(2, RoundingMode.HALF_UP);

        // 3. Calcular el importe del descuento.
        BigDecimal montoDescuento = subtotal
                .multiply(descuentoPorcentaje)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        // 4. Calcular el precio final.
        BigDecimal precioFinal = subtotal.subtract(montoDescuento)
                .setScale(2, RoundingMode.HALF_UP);

        // 5. Actualizar los datos generales.
        presupuesto.setNombreProyecto(dto.getNombreProyecto());
        presupuesto.setObservaciones(dto.getObservaciones());
        presupuesto.setSubtotal(subtotal);
        presupuesto.setDescuentoPorcentaje(descuentoPorcentaje);
        presupuesto.setMontoDescuento(montoDescuento);
        presupuesto.setPrecioEstimado(precioFinal);

        // 6. Eliminar los detalles anteriores.
        // Se conserva la colección administrada por Hibernate.
        presupuesto.getDetalles().clear();

        // 7. Agregar los nuevos detalles.
        for (PresupuestoDetalleDTORequest detalleDTO : dto.getDetalles()) {

            PresupuestoDetalleEntity detalle = PresupuestoDetalleEntity.builder()
                    .presupuesto(presupuesto)
                    .descripcion(detalleDTO.getDescripcion())
                    .precioUnitario(detalleDTO.getPrecioUnitario())
                    .cantidad(detalleDTO.getCantidad())
                    .build();

            presupuesto.getDetalles().add(detalle);
        }
    }
}
