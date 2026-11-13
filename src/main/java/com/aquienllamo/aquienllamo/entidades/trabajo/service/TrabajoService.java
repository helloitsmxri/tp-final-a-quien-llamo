package com.aquienllamo.aquienllamo.entidades.trabajo.service;

import com.aquienllamo.aquienllamo.APIs.GoogleGmail.EmailService;
import com.aquienllamo.aquienllamo.auth.credentials.CredentialsEntity;
import com.aquienllamo.aquienllamo.auth.permissions.RolesUser;
import com.aquienllamo.aquienllamo.entidades.presupuesto.enums.EstadoPresupuestoE;
import com.aquienllamo.aquienllamo.entidades.presupuesto.entity.PresupuestoEntity;
import com.aquienllamo.aquienllamo.entidades.presupuesto.exceptions.PresupuestoNotFoundEx;
import com.aquienllamo.aquienllamo.entidades.presupuesto.repository.PresupuestoRepository;
import com.aquienllamo.aquienllamo.entidades.trabajo.dto.request.TrabajoDTORequest;
import com.aquienllamo.aquienllamo.entidades.trabajo.dto.response.TrabajoDTOResponse;
import com.aquienllamo.aquienllamo.entidades.trabajo.entity.TrabajoEntity;
import com.aquienllamo.aquienllamo.entidades.trabajo.enums.EstadoTrabajo;
import com.aquienllamo.aquienllamo.entidades.trabajo.exceptions.TrabajoAlreadyExistsEx;
import com.aquienllamo.aquienllamo.entidades.trabajo.exceptions.TrabajoNotAcceptedEx;
import com.aquienllamo.aquienllamo.entidades.trabajo.exceptions.TrabajoNotFoundEx;
import com.aquienllamo.aquienllamo.entidades.trabajo.mapper.TrabajoMapper;
import com.aquienllamo.aquienllamo.entidades.trabajo.repository.TrabajoRepository;
import com.aquienllamo.aquienllamo.entidades.usuario.entities.UsuarioEntity;
import com.aquienllamo.aquienllamo.entidades.usuario.repository.UsuarioRepository;
import com.aquienllamo.aquienllamo.util.SecurityUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class TrabajoService {

    private final TrabajoRepository trabajoRepository;
    private final PresupuestoRepository presupuestoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EmailService emailService;

    // =====================================================
    // MÉTODOS AUXILIARES DE AUTENTICACIÓN Y AUTORIZACIÓN
    // =====================================================

    private CredentialsEntity obtenerCredenciales() {
        return SecurityUtils.getCurrentCredentials();
    }

    private UsuarioEntity obtenerUsuarioAutenticado() {
        return obtenerCredenciales().getUsuario();
    }

    private boolean esAdministrador(CredentialsEntity credentials) {
        return credentials.hasRole(RolesUser.ROLE_ADMINISTRADOR)
                || credentials.hasRole(RolesUser.ROLE_SUPERADMINISTRADOR);
    }

    private void exigirAdministrador(CredentialsEntity credentials) {
        if (!esAdministrador(credentials)) {
            throw new AccessDeniedException(
                    "No tiene permisos para realizar esta accion.");
        }
    }

    // =====================================================
    // CREAR TRABAJO
    // =====================================================

    public TrabajoDTOResponse crearTrabajo(TrabajoDTORequest request) {

        UsuarioEntity usuario = obtenerUsuarioAutenticado();

        PresupuestoEntity presupuesto = presupuestoRepository
                .findByUuid(request.getUuidPresupuesto())
                .orElseThrow(() -> new PresupuestoNotFoundEx(
                        "ERROR: El presupuesto ingresado no existe."));

        // Verificar que el técnico del presupuesto sea el autenticado.
        if (presupuesto.getTecnico() == null ||
                presupuesto.getTecnico().getUsuario() == null ||
                !Objects.equals(
                        presupuesto.getTecnico().getUsuario().getIdUsuario(),
                        usuario.getIdUsuario())) {

            throw new AccessDeniedException(
                    "Solo el tecnico de este presupuesto puede crear el trabajo.");
        }

        // El presupuesto debe estar aceptado.
        if (presupuesto.getEstado() != EstadoPresupuestoE.Aceptado) {
            throw new PresupuestoNotFoundEx(
                    "ERROR: Solo se pueden crear trabajos con presupuestos aceptados.");
        }

        // Un presupuesto solamente puede tener un trabajo.
        if (trabajoRepository.existsByPresupuestoUuid(request.getUuidPresupuesto())) {
            throw new TrabajoAlreadyExistsEx(
                    "ERROR: El trabajo ya existe.");
        }

        TrabajoEntity trabajo = TrabajoMapper.toEntity(request, presupuesto);

        // El estado inicial siempre lo establece el backend.
        trabajo.setEstadoTrabajo(EstadoTrabajo.Pendiente);

        trabajoRepository.save(trabajo);

        // Notificar al cliente.
        emailService.enviarTrabajoCreado(
                presupuesto.getUsuario().getEmail(),
                trabajo.getDescripcionTrabajo()
        );

        return TrabajoMapper.toResponse(trabajo);
    }

    // =====================================================
    // LISTAR TODOS LOS TRABAJOS
    // =====================================================

    public List<TrabajoDTOResponse> listarTrabajos() {

        CredentialsEntity credentials = obtenerCredenciales();

        exigirAdministrador(credentials);

        return trabajoRepository.findAll()
                .stream()
                .map(TrabajoMapper::toResponse)
                .toList();
    }

    // =====================================================
    // LISTAR TRABAJOS POR ESTADO
    // =====================================================

    public List<TrabajoDTOResponse> listarTrabajoPorEstadoTrabajo(
            EstadoTrabajo estadoTrabajo) {

        CredentialsEntity credentials = obtenerCredenciales();

        exigirAdministrador(credentials);

        return trabajoRepository.findAllByEstadoTrabajo(estadoTrabajo)
                .stream()
                .map(TrabajoMapper::toResponse)
                .toList();
    }

    // =====================================================
    // LISTAR MIS TRABAJOS
    // =====================================================

    public List<TrabajoDTOResponse> listarMisTrabajos() {

        UsuarioEntity usuario = obtenerUsuarioAutenticado();

        return trabajoRepository.findAllByParticipante(usuario.getEmail())
                .stream()
                .map(TrabajoMapper::toResponse)
                .toList();
    }

    // =====================================================
    // CAMBIAR ESTADO DEL TRABAJO
    // =====================================================

    public TrabajoDTOResponse cambiarEstadoTrabajo(
            String uuidTrabajo,
            EstadoTrabajo nuevoEstado) {

        CredentialsEntity credentials = obtenerCredenciales();
        UsuarioEntity usuario = credentials.getUsuario();

        TrabajoEntity trabajo = trabajoRepository.findByUuid(uuidTrabajo)
                .orElseThrow(() -> new TrabajoNotFoundEx(
                        "ERROR: El trabajo ingresado no existe"));

        boolean esAdmin = esAdministrador(credentials);

        // Si no es administrador, debe ser el técnico del trabajo.
        if (!esAdmin && !esElTecnico(trabajo, usuario)) {
            throw new AccessDeniedException(
                    "Solo el tecnico de este trabajo puede cambiar su estado.");
        }

        validarTransicion(trabajo.getEstadoTrabajo(), nuevoEstado);

        trabajo.setEstadoTrabajo(nuevoEstado);

        return TrabajoMapper.toResponse(trabajoRepository.save(trabajo));
    }

    // =====================================================
    // LISTAR TRABAJOS POR CLIENTE
    // =====================================================

    public List<TrabajoDTOResponse> listarTrabajosPorCliente(String uuidCliente) {

        CredentialsEntity credentials = obtenerCredenciales();
        UsuarioEntity usuario = credentials.getUsuario();

        if (!esAdministrador(credentials) &&
                !perteneceAlUsuario(uuidCliente)) {

            throw new AccessDeniedException(
                    "No tiene permiso para consultar estos trabajos.");
        }

        return trabajoRepository.findAllByUuidUsuario(uuidCliente)
                .stream()
                .map(TrabajoMapper::toResponse)
                .toList();
    }

    // =====================================================
    // LISTAR TRABAJOS POR TÉCNICO
    // =====================================================

    public List<TrabajoDTOResponse> listarTrabajosPorTecnico(String uuidTecnico) {

        CredentialsEntity credentials = obtenerCredenciales();

        if (!esAdministrador(credentials)) {

            if (!credentials.hasRole(RolesUser.ROLE_TECNICO) ||
                    !perteneceAlUsuario(uuidTecnico)) {

                throw new AccessDeniedException(
                        "No tiene permiso para consultar estos trabajos.");
            }
        }

        return trabajoRepository.findAllByUuidTecnico(uuidTecnico)
                .stream()
                .map(TrabajoMapper::toResponse)
                .toList();
    }

    // =====================================================
    // VERIFICAR PERTENENCIA AL USUARIO AUTENTICADO
    // =====================================================

    public boolean perteneceAlUsuario(String uuidUsuario) {

        UsuarioEntity usuario = obtenerUsuarioAutenticado();

        return usuarioRepository.findByUuid(uuidUsuario)
                .map(usuarioBuscado ->
                        Objects.equals(
                                usuarioBuscado.getIdUsuario(),
                                usuario.getIdUsuario()
                        ))
                .orElse(false);
    }

    // =====================================================
    // OBTENER TRABAJO POR UUID
    // =====================================================

    public TrabajoDTOResponse obtenerTrabajoPorUuid(String uuidTrabajo) {

        CredentialsEntity credentials = obtenerCredenciales();
        UsuarioEntity usuario = credentials.getUsuario();

        TrabajoEntity trabajo = trabajoRepository.findByUuid(uuidTrabajo)
                .orElseThrow(() -> new TrabajoNotFoundEx(
                        "ERROR: El trabajo ingresado no existe"));

        boolean esAdmin = esAdministrador(credentials);

        // Puede acceder el administrador, técnico o cliente.
        if (!esAdmin &&
                !esElTecnico(trabajo, usuario) &&
                !esElCliente(trabajo, usuario)) {

            throw new AccessDeniedException(
                    "No tiene permiso para ver este trabajo.");
        }

        return TrabajoMapper.toResponse(trabajo);
    }

    // =====================================================
    // VERIFICAR SI ES EL TÉCNICO
    // =====================================================

    private boolean esElTecnico(
            TrabajoEntity trabajo,
            UsuarioEntity usuario) {

        return trabajo.getPresupuesto() != null &&
                trabajo.getPresupuesto().getTecnico() != null &&
                trabajo.getPresupuesto().getTecnico().getUsuario() != null &&
                Objects.equals(
                        trabajo.getPresupuesto()
                                .getTecnico()
                                .getUsuario()
                                .getIdUsuario(),
                        usuario.getIdUsuario()
                );
    }

    // =====================================================
    // VERIFICAR SI ES EL CLIENTE
    // =====================================================

    private boolean esElCliente(
            TrabajoEntity trabajo,
            UsuarioEntity usuario) {

        return trabajo.getPresupuesto() != null &&
                trabajo.getPresupuesto().getUsuario() != null &&
                Objects.equals(
                        trabajo.getPresupuesto()
                                .getUsuario()
                                .getIdUsuario(),
                        usuario.getIdUsuario()
                );
    }

    // =====================================================
    // VALIDAR TRANSICIÓN DE ESTADOS
    // =====================================================

    private void validarTransicion(
            EstadoTrabajo actual,
            EstadoTrabajo nuevo) {

        boolean permitido = switch (actual) {

            case Pendiente ->
                    nuevo == EstadoTrabajo.En_proceso ||
                            nuevo == EstadoTrabajo.Cancelado;

            case En_proceso ->
                    nuevo == EstadoTrabajo.Finalizado ||
                            nuevo == EstadoTrabajo.Cancelado;

            case Finalizado, Cancelado -> false;
        };

        if (!permitido) {
            throw new TrabajoNotAcceptedEx(
                    "ERROR: No se puede pasar el trabajo de "
                            + actual + " a " + nuevo + ".");
        }
    }
}