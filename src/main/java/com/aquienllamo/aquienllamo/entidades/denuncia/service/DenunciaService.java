package com.aquienllamo.aquienllamo.entidades.denuncia.service;

import com.aquienllamo.aquienllamo.APIs.GoogleGmail.EmailService;
import com.aquienllamo.aquienllamo.administrador.exceptions.AdminAsignadoDenunciaEx;
import com.aquienllamo.aquienllamo.administrador.exceptions.AdministradorNotFoundEx;
import com.aquienllamo.aquienllamo.auth.credentials.CredentialsEntity;
import com.aquienllamo.aquienllamo.auth.permissions.RolesUser;
import com.aquienllamo.aquienllamo.auth.repositories.CredentialsRepository;
import com.aquienllamo.aquienllamo.entidades.chat.entity.ChatEntity;
import com.aquienllamo.aquienllamo.entidades.chat.exceptions.ChatNotFoundEx;
import com.aquienllamo.aquienllamo.entidades.chat.repository.ChatRepository;
import com.aquienllamo.aquienllamo.entidades.denuncia.dto.request.DenunciaDTORequest;
import com.aquienllamo.aquienllamo.entidades.denuncia.dto.response.DenunciaDTOResponse;
import com.aquienllamo.aquienllamo.entidades.denuncia.dto.response.DenunciaFotoDTO;
import com.aquienllamo.aquienllamo.entidades.denuncia.entity.DenunciaEntity;
import com.aquienllamo.aquienllamo.entidades.denuncia.enums.EstadoDenunciaE;
import com.aquienllamo.aquienllamo.entidades.denuncia.exceptions.DenunciaNotFoundEx;
import com.aquienllamo.aquienllamo.entidades.denuncia.exceptions.DenunciaResueltaEx;
import com.aquienllamo.aquienllamo.entidades.denuncia.mapper.DenunciaMapper;
import com.aquienllamo.aquienllamo.entidades.denuncia.repository.DenunciaRepository;
import com.aquienllamo.aquienllamo.entidades.usuario.entities.UsuarioEntity;
import com.aquienllamo.aquienllamo.entidades.usuario.repository.UsuarioRepository;
import com.aquienllamo.aquienllamo.exception.generales.ImageDataTypeNotFoundEx;
import com.aquienllamo.aquienllamo.util.SecurityUtils;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DenunciaService {

    private static final String EMAIL_ADMINISTRACION =
            "aquienllamoinfo@gmail.com";

    private static final String URL_BASE =
            "http://localhost:8080/aquienllamo/denuncias/";

    private final DenunciaRepository denunciaRepository;
    private final DenunciaMapper denunciaMapper;
    private final UsuarioRepository usuarioRepository;
    private final ChatRepository chatRepository;
    private final EmailService emailService;
    private final CredentialsRepository credentialsRepository;


    // CREAR DENUNCIA DESDE UN CHAT

    public DenunciaDTOResponse crearDenuncia(
            DenunciaDTORequest dto,
            String uuidChat
    ) {

        UsuarioEntity denunciante = obtenerUsuarioActual();

        ChatEntity chat = chatRepository.findByUuidChat(uuidChat)
                .orElseThrow(() ->
                        new ChatNotFoundEx("No se encontró el chat indicado.")
                );

        UsuarioEntity usuarioDelChat = chat.getUsuario();

        UsuarioEntity tecnicoDelChat =
                chat.getTecnico().getUsuario();

        UsuarioEntity denunciado;

        if (usuarioDelChat.getUuid().equals(denunciante.getUuid())) {

            denunciado = tecnicoDelChat;

        } else if (tecnicoDelChat.getUuid().equals(denunciante.getUuid())) {

            denunciado = usuarioDelChat;

        } else {

            throw new AccessDeniedException(
                    "No participás de este chat."
            );
        }

        return guardarNuevaDenuncia(
                dto,
                denunciante,
                denunciado
        );
    }


    // CREAR DENUNCIA DESDE EL PERFIL DE OTRO USUARIO

    public DenunciaDTOResponse crearDenunciaDesdePerfil(
            DenunciaDTORequest dto,
            String uuidDenunciado
    ) {

        UsuarioEntity denunciante = obtenerUsuarioActual();

        UsuarioEntity denunciado = usuarioRepository
                .findByUuid(uuidDenunciado)
                .orElseThrow(() ->
                        new DenunciaNotFoundEx(
                                "No se encontró el usuario que querés denunciar."
                        )
                );

        return guardarNuevaDenuncia(
                dto,
                denunciante,
                denunciado
        );
    }


    // METODO COMPARTIDO PARA GUARDAR UNA DENUNCIA

    private DenunciaDTOResponse guardarNuevaDenuncia(
            DenunciaDTORequest dto,
            UsuarioEntity denunciante,
            UsuarioEntity denunciado
    ) {

        if (denunciante.getUuid().equals(denunciado.getUuid())) {

            throw new AccessDeniedException(
                    "No podés denunciar tu propia cuenta."
            );
        }

        DenunciaEntity nueva = denunciaMapper.toEntity(dto);

        nueva.setDenunciante(denunciante);
        nueva.setDenunciado(denunciado);

        nueva.setEstadoDenuncia(EstadoDenunciaE.Pendiente);

        // La columna fecha_denuncia es NOT NULL y no tiene DEFAULT.
        nueva.setFechaDenuncia(LocalDateTime.now());

        if (dto.getFoto() != null && !dto.getFoto().isEmpty()) {

            try {

                nueva.setFoto(dto.getFoto().getBytes());

                nueva.setTipoFoto(
                        dto.getFoto().getContentType()
                );

            } catch (IOException ex) {

                throw new ImageDataTypeNotFoundEx(
                        "No se pudo procesar la foto adjunta."
                );
            }
        }

        DenunciaEntity guardada =
                denunciaRepository.save(nueva);

        emailService.enviarDenunciaAdmin(
                EMAIL_ADMINISTRACION,
                guardada.getUuid()
        );

        emailService.enviarSeCreoDenunciaDenunciante(
                denunciante.getEmail(),
                denunciante.getNombre(),
                URL_BASE + guardada.getUuid()
        );

        emailService.enviarSeCreoDenunciaDenunciado(
                denunciado.getEmail(),
                denunciado.getNombre(),
                URL_BASE + guardada.getUuid()
        );

        return denunciaMapper.toResponse(guardada);
    }


    // LISTAR TODAS LAS DENUNCIAS: ADMINISTRACIÓN

    public List<DenunciaDTOResponse> listarDenuncias() {

        return denunciaRepository.findAll()
                .stream()
                .map(denunciaMapper::toResponse)
                .toList();
    }


    // BUSCAR UNA DENUNCIA POR UUID

    public DenunciaDTOResponse buscarPorUuid(String uuid) {

        DenunciaEntity denuncia = obtenerDenuncia(uuid);

        validarAccesoDenuncia(denuncia);

        return denunciaMapper.toResponse(denuncia);
    }


    // LISTAR POR ESTADO

    public List<DenunciaDTOResponse> listarPorEstado(
            EstadoDenunciaE estado
    ) {

        return denunciaRepository.findByEstadoDenuncia(estado)
                .stream()
                .map(denunciaMapper::toResponse)
                .toList();
    }


    // ASIGNAR ADMINISTRADOR

    public DenunciaDTOResponse asignarAdministrador(
            String uuidDenuncia,
            String uuidAdmin
    ) {

        DenunciaEntity denuncia = obtenerDenuncia(uuidDenuncia);

        if (denuncia.getAdministrador() != null) {

            throw new AdminAsignadoDenunciaEx(
                    "La denuncia ya tiene un administrador asignado."
            );
        }

        if (denuncia.getEstadoDenuncia() != EstadoDenunciaE.Pendiente) {

            throw new DenunciaResueltaEx(
                    "Solo se pueden asignar denuncias pendientes."
            );
        }

        UsuarioEntity administrador = usuarioRepository
                .findByUuid(uuidAdmin)
                .orElseThrow(() ->
                        new AdministradorNotFoundEx(
                                "No se encontró el administrador indicado."
                        )
                );

        CredentialsEntity credencialesAdmin =
                credentialsRepository.findByUsuario(administrador)
                        .orElseThrow(() ->
                                new AdministradorNotFoundEx(
                                        "El usuario indicado no tiene credenciales."
                                )
                        );

        if (!esAdministrador(credencialesAdmin)) {

            throw new AdministradorNotFoundEx(
                    "Solo se puede asignar una denuncia a un administrador o superadministrador."
            );
        }

        denuncia.setAdministrador(administrador);

        denuncia.setEstadoDenuncia(
                EstadoDenunciaE.En_proceso
        );

        DenunciaEntity guardada =
                denunciaRepository.save(denuncia);

        emailService.enviarSeAsignoUnAdmin(
                guardada.getDenunciante().getEmail(),
                guardada.getDenunciante().getNombre(),
                URL_BASE + guardada.getUuid()
        );

        emailService.enviarSeAsignoUnAdmin2(
                guardada.getDenunciado().getEmail(),
                guardada.getDenunciado().getNombre(),
                URL_BASE + guardada.getUuid()
        );

        return denunciaMapper.toResponse(guardada);
    }


    // DENUNCIAS SIN ASIGNAR

    public List<DenunciaDTOResponse> obtenerDenunciasSinAsignar() {

        return denunciaRepository.findByAdministradorIsNull()
                .stream()
                .map(denunciaMapper::toResponse)
                .toList();
    }


    // APROBAR DENUNCIA

    public DenunciaDTOResponse aprobarDenuncia(
            String uuidDenuncia,
            String mensaje
    ) {

        DenunciaEntity denuncia = obtenerDenuncia(uuidDenuncia);

        validarPermisoParaResolver(denuncia);

        denuncia.setEstadoDenuncia(
                EstadoDenunciaE.Aprobada
        );

        denuncia.setNotaDelAdmin(mensaje);

        DenunciaEntity guardada =
                denunciaRepository.save(denuncia);

        emailService.enviarDenunciaAprobadaDenunciante(
                guardada.getDenunciante().getEmail(),
                guardada.getDenunciante().getNombre(),
                URL_BASE + guardada.getUuid()
        );

        emailService.enviarDenunciaAprobadaDenunciado(
                guardada.getDenunciado().getEmail(),
                guardada.getDenunciado().getNombre(),
                URL_BASE + guardada.getUuid()
        );

        return denunciaMapper.toResponse(guardada);
    }


    // RECHAZAR DENUNCIA

    public DenunciaDTOResponse rechazarDenuncia(
            String uuidDenuncia,
            String mensaje
    ) {

        DenunciaEntity denuncia = obtenerDenuncia(uuidDenuncia);

        validarPermisoParaResolver(denuncia);

        denuncia.setEstadoDenuncia(
                EstadoDenunciaE.Rechazada
        );

        denuncia.setNotaDelAdmin(mensaje);

        DenunciaEntity guardada =
                denunciaRepository.save(denuncia);

        emailService.enviarDenunciaRechazadaDenunciante(
                guardada.getDenunciante().getEmail(),
                guardada.getDenunciante().getNombre(),
                URL_BASE + guardada.getUuid()
        );

        emailService.enviarDenunciaRechazadaDenunciado(
                guardada.getDenunciado().getEmail(),
                guardada.getDenunciado().getNombre(),
                URL_BASE + guardada.getUuid()
        );

        return denunciaMapper.toResponse(guardada);
    }


    // VALIDAR QUIÉN PUEDE RESOLVER UNA DENUNCIA

    private void validarPermisoParaResolver(
            DenunciaEntity denuncia
    ) {

        if (denuncia.getEstadoDenuncia()
                != EstadoDenunciaE.En_proceso) {

            throw new DenunciaResueltaEx(
                    "La denuncia debe estar en proceso y todavía no debe haber sido resuelta."
            );
        }

        CredentialsEntity credenciales =
                SecurityUtils.getCurrentCredentials();

        if (!esAdministrador(credenciales)) {

            throw new AccessDeniedException(
                    "La cuenta no tiene permisos de administración."
            );
        }

        boolean esSuperadministrador =
                credenciales.hasRole(
                        RolesUser.ROLE_SUPERADMINISTRADOR
                );

        String uuidAdminActual =
                credenciales.getUsuario().getUuid();

        if (!esSuperadministrador
                && (denuncia.getAdministrador() == null
                || !denuncia.getAdministrador()
                .getUuid()
                .equals(uuidAdminActual))) {

            throw new AdminAsignadoDenunciaEx(
                    "Solo el administrador asignado puede resolver esta denuncia."
            );
        }
    }


    // ORDENAR DENUNCIAS

    public List<DenunciaDTOResponse> denunciasMasViejasPrimero() {

        return denunciaRepository.findAllByOrderByFechaDenunciaAsc()
                .stream()
                .map(denunciaMapper::toResponse)
                .toList();
    }

    public List<DenunciaDTOResponse> denunciasMasNuevasPrimero() {

        return denunciaRepository.findAllByOrderByFechaDenunciaDesc()
                .stream()
                .map(denunciaMapper::toResponse)
                .toList();
    }


    // DENUNCIAS QUE REALIZÓ EL USUARIO AUTENTICADO

    public List<DenunciaDTOResponse> misDenunciasCreadas() {

        String uuidUsuario =
                obtenerUsuarioActual().getUuid();

        return denunciaRepository
                .findByDenunciante_UuidOrderByFechaDenunciaDesc(
                        uuidUsuario
                )
                .stream()
                .map(denunciaMapper::toResponse)
                .toList();
    }


    // DENUNCIAS QUE RECIBIÓ EL USUARIO AUTENTICADO

    public List<DenunciaDTOResponse> misDenunciasRecibidas() {

        String uuidUsuario =
                obtenerUsuarioActual().getUuid();

        return denunciaRepository
                .findByDenunciado_UuidOrderByFechaDenunciaDesc(
                        uuidUsuario
                )
                .stream()
                .map(denunciaMapper::toResponse)
                .toList();
    }


    // DENUNCIAS ASIGNADAS AL ADMINISTRADOR AUTENTICADO

    public List<DenunciaDTOResponse> misDenuncias(
            EstadoDenunciaE estado
    ) {

        CredentialsEntity credenciales =
                SecurityUtils.getCurrentCredentials();

        if (!esAdministrador(credenciales)) {

            throw new AccessDeniedException(
                    "La cuenta no pertenece a un administrador."
            );
        }

        String uuidAdmin =
                credenciales.getUsuario().getUuid();

        List<DenunciaEntity> denuncias;

        if (estado == null) {

            denuncias =
                    denunciaRepository.findByAdministradorUuid(
                            uuidAdmin
                    );

        } else {

            denuncias =
                    denunciaRepository.findByAdministradorUuidAndEstadoDenuncia(
                            uuidAdmin,
                            estado
                    );
        }

        return denuncias.stream()
                .map(denunciaMapper::toResponse)
                .toList();
    }


    // MÉTODOS AUXILIARES

    private DenunciaEntity obtenerDenuncia(String uuid) {

        return denunciaRepository.findByUuid(uuid)
                .orElseThrow(() ->
                        new DenunciaNotFoundEx(
                                "No se encontró la denuncia con ese UUID."
                        )
                );
    }

    private UsuarioEntity obtenerUsuarioActual() {

        return SecurityUtils.getCurrentUser();
    }

    private boolean esAdministrador(
            CredentialsEntity credenciales
    ) {

        return credenciales.hasRole(
                RolesUser.ROLE_ADMINISTRADOR
        )
                ||
                credenciales.hasRole(
                        RolesUser.ROLE_SUPERADMINISTRADOR
                );
    }

    // OBTENER FOTO DE UNA DENUNCIA

    public DenunciaFotoDTO obtenerFoto(String uuid) {

        DenunciaEntity denuncia = obtenerDenuncia(uuid);

        // Verificar que el usuario tenga permiso para consultar la denuncia.
        validarAccesoDenuncia(denuncia);

        // Verificar que exista una foto.
        if (denuncia.getFoto() == null || denuncia.getFoto().length == 0) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Esta denuncia no tiene una foto adjunta."
            );
        }

        String tipoContenido = denuncia.getTipoFoto();

        if (tipoContenido == null || tipoContenido.isBlank()) {
            tipoContenido = "application/octet-stream";
        }

        return new DenunciaFotoDTO(
                denuncia.getFoto(),
                tipoContenido
        );
    }

    // VALIDAR ACCESO A UNA DENUNCIA

    private void validarAccesoDenuncia(DenunciaEntity denuncia) {

        CredentialsEntity credenciales =
                SecurityUtils.getCurrentCredentials();

        boolean esAdmin = esAdministrador(credenciales);

        String uuidUsuarioActual =
                credenciales.getUsuario().getUuid();

        boolean esParticipante =
                denuncia.getDenunciante().getUuid()
                        .equals(uuidUsuarioActual)
                        ||
                        denuncia.getDenunciado().getUuid()
                                .equals(uuidUsuarioActual);

        if (!esAdmin && !esParticipante) {
            throw new AccessDeniedException(
                    "No tenés permiso para consultar esta denuncia."
            );
        }
    }

}