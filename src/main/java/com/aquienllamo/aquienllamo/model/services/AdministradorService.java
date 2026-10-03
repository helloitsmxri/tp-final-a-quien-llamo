package com.aquienllamo.aquienllamo.model.services;


import com.aquienllamo.aquienllamo.model.APIs.GoogleGmail.EmailService;
import com.aquienllamo.aquienllamo.model.auth.credentials.CredentialsEntity;
import com.aquienllamo.aquienllamo.model.auth.permissions.RoleEntity;
import com.aquienllamo.aquienllamo.model.auth.permissions.RolesUser;
import com.aquienllamo.aquienllamo.model.auth.repositories.CredentialsRepository;
import com.aquienllamo.aquienllamo.model.auth.repositories.RoleRepository;

import com.aquienllamo.aquienllamo.model.auth.utils.SecurityUtils;
import com.aquienllamo.aquienllamo.model.dtos.Request.AsignRolDTORequest;
import com.aquienllamo.aquienllamo.model.entities.UsuarioEntity;
import com.aquienllamo.aquienllamo.model.exceptions.*;
import com.aquienllamo.aquienllamo.model.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class AdministradorService {

        private final UsuarioRepository usuarioRepository;
        private final CredentialsRepository credentialsRepository;
        private final RoleRepository roleRepository;
        private final EmailService emailService;


        public String asigneRol(AsignRolDTORequest request) {

            UsuarioEntity user = usuarioRepository.findByUuid(request.getUuid())
                    .orElseThrow(() -> new UserNotFoundEx("No se encontró el usuario"));


            RoleEntity newRole = roleRepository.findByRole(request.getRol())
                    .orElseThrow(() -> new RuntimeException("No se encontro el rol seleccionado"));

            if (newRole.getRole() == RolesUser.ROLE_SUPERADMINISTRADOR) {
                throw new RuntimeException("Imposible asignar el rol");
            }


            CredentialsEntity usuarioAutenticado = SecurityUtils.getCurrentCredentials();

            if (newRole.getRole() == RolesUser.ROLE_ADMINISTRADOR && !usuarioAutenticado.hasRole(RolesUser.ROLE_SUPERADMINISTRADOR)) {
                throw new RuntimeException("El usuario autenticado no tiene los permisos suficientes");
            }

            CredentialsEntity cred = credentialsRepository.findByUsuario(user)
                    .orElseThrow(() -> new UserNotFoundEx("No se encontró el usuario"));

            if (cred.getRoles().contains(newRole)){
                throw new RuntimeException("El usuario ya posee el rol asignado");
            }

            cred.addRole(newRole);

            // guardar credenciales
            credentialsRepository.save(cred);

            return "Rol asignado con exito";
        }

        public String removeRolAdmin(String uuid){


            UsuarioEntity user = usuarioRepository.findByUuid(uuid)
                    .orElseThrow(() -> new UserNotFoundEx("No se encontró el usuario"));

            CredentialsEntity cred = credentialsRepository.findByUsuario(user)
                    .orElseThrow(() -> new UserNotFoundEx("No se encontró el usuario"));

            if (!cred.hasRole(RolesUser.ROLE_ADMINISTRADOR)) {
                throw new RuntimeException("El usuario no posee el rol Administrador");
            }

            CredentialsEntity usuarioAutenticado = SecurityUtils.getCurrentCredentials();


            if (!usuarioAutenticado.hasRole(RolesUser.ROLE_SUPERADMINISTRADOR)) {
                throw new RuntimeException("El usuario autenticado no posee los permisos suficientes remover rol de administrador");
            }

            cred.removeRole(RolesUser.ROLE_ADMINISTRADOR);

            // guardar credenciales
            credentialsRepository.save(cred);

            return "Rol eliminado con exito";
        }


    // se me ocurrió q para amonestar sea un mes de baja
    public String amonestarUsuario(String uuid, String motivo){
        UsuarioEntity user = usuarioRepository.findByUuid(uuid)
                .orElseThrow(() -> new UserNotFoundEx("No se encontró el usuario"));

        // esto es una validación extra, para q si el user ya está INACTIVO no pueda darlo de baja
        if (!Boolean.TRUE.equals(user.getActivo())){
            throw new RuntimeException("No se puede amonestar a un usuario dado de baja.");
        }

        // si la fecha de suspensión no es nula y además el fin de la misma es DESPUÉS de la fecha actual
        // no lo puede amonestar de nuevo. xq ya está amonestado
        if (user.getFechaFinSuspension() != null &&
                user.getFechaFinSuspension().isAfter(LocalDate.now())){
            throw new UserAlreadySuspendedEx("El usuario ya se encuentra suspendido.");
        }

        user.setFechaFinSuspension(LocalDate.now().plusMonths(1));
        usuarioRepository.save(user);
        emailService.enviarSuspensionCuenta(user.getEmail(), motivo);

        return "Se ha suspendido al usuario por un mes";
    }

    public String darDeBajaUsuario(String uuid){
        UsuarioEntity user = usuarioRepository.findByUuid(uuid)
                .orElseThrow(() -> new UserNotFoundEx("No se encontró el usuario"));

        // si el user no está activo entonces significa q está dado de baja ya

        if (!Boolean.TRUE.equals(user.getActivo())){
            throw new UserAlreadyDisabledEx("El usuario ya se encuentra dado de baja.");
        }

        user.setActivo(false);

        // y si ya estaba suspendido desde antes, le sacamos la suspensión y simplemente lo bajamos
        user.setFechaFinSuspension(null);

        usuarioRepository.save(user);

        return "Usuario dado de baja correctamente";
    }

    // "rehabilitar" un usuario, o sea, sacarle la baja de la cuenta.
    public String quitarBaja(String uuid){
        UsuarioEntity user = usuarioRepository.findByUuid(uuid)
                .orElseThrow(() -> new UserNotFoundEx("El usuario no se encontró"));

        // si el usuario ya está activo
        if (Boolean.TRUE.equals(user.getActivo())){
            throw new DisabledProfileEx("El usuario no está dado de baja.");
        }

        user.setActivo(true);
        usuarioRepository.save(user);

        return "Usuario rehabilitado (se le concedió nuevamente el acceso a la plataforma)";
    }

    // rehabilitar el usuario -> sacarle la suspensión x cualq razón
    public String quitarSuspension(String uuid){
        UsuarioEntity user = usuarioRepository.findByUuid(uuid)
                .orElseThrow(() -> new UserNotFoundEx("El usuario no se encontró"));

        // si el usuario ya está activo o la fecha de suspensión NO es dsps de la fecha de hoy
        if (user.getFechaFinSuspension() == null ||
                !user.getFechaFinSuspension().isAfter(LocalDate.now())){
            throw new UserSuspendedException("El usuario no está suspendido.");
        }

        user.setFechaFinSuspension(null);
        usuarioRepository.save(user);

        return "Se le ha quitado exitosamente la suspensión al usuario.";
    }

}
