package com.aquienllamo.aquienllamo.model.services;


import com.aquienllamo.aquienllamo.model.auth.Credentials.CredentialsEntity;
import com.aquienllamo.aquienllamo.model.auth.permissions.RoleEntity;
import com.aquienllamo.aquienllamo.model.auth.permissions.RolesUser;
import com.aquienllamo.aquienllamo.model.auth.repositories.CredentialsRepository;
import com.aquienllamo.aquienllamo.model.auth.repositories.RoleRepository;

import com.aquienllamo.aquienllamo.model.dtos.Request.AsignRolDTORequest;
import com.aquienllamo.aquienllamo.model.entities.UsuarioEntity;
import com.aquienllamo.aquienllamo.model.exceptions.UserNotFoundEx;
import com.aquienllamo.aquienllamo.model.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class AdministradorService {

    private final UsuarioRepository usuarioRepository;
        private final CredentialsRepository credentialsRepository;
        private final RoleRepository roleRepository;


        public String asigneRol(AsignRolDTORequest request) {

            UsuarioEntity user = usuarioRepository.findByUuid(request.getUuid())
                    .orElseThrow(() -> new UserNotFoundEx("No se encontró el usuario"));


            RoleEntity newRole = roleRepository.findByRole(request.getRol())
                    .orElseThrow(() -> new RuntimeException("No se encontro el rol seleccionado"));

            if (newRole.equals(roleRepository.findByRole(RolesUser.ROLE_SUPERADMINISTRADOR))) {
                throw new RuntimeException("No se puede cambiar al rol asignado");
            }

            CredentialsEntity cred = credentialsRepository.findByUsuario(user)
                    .orElseThrow(() -> new UserNotFoundEx("No se encontró el usuario"));

            if (cred.getRoles().contains(newRole)){
                throw new RuntimeException("El usuario ya posee el rol asignado");
            }

            cred.getRoles().add(newRole);

            // guardar credenciales
            credentialsRepository.save(cred);

            return "Rol asignado con exito";
        }

        public String removeRolAdmin(String uuid){

            UsuarioEntity user = usuarioRepository.findByUuid(uuid)
                    .orElseThrow(() -> new UserNotFoundEx("No se encontró el usuario"));

            CredentialsEntity cred = credentialsRepository.findByUsuario(user)
                    .orElseThrow(() -> new UserNotFoundEx("No se encontró el usuario"));

            if(!cred.getRoles().contains(RolesUser.ROLE_SUPERADMINISTRADOR)){
                throw new RuntimeException("El usuario no posee el rol Administrador");
            }

            cred.getRoles().remove(RolesUser.ROLE_ADMINISTRADOR);

            // guardar credenciales
            credentialsRepository.save(cred);

            return "Rol eliminado con exito";
        }

}
