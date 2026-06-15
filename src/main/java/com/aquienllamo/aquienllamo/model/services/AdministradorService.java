package com.aquienllamo.aquienllamo.model.services;

import com.aquienllamo.aquienllamo.model.auth.Credentials.CredentialsEntity;
import com.aquienllamo.aquienllamo.model.auth.JWT.JwtService;
import com.aquienllamo.aquienllamo.model.auth.permissions.RoleEntity;
import com.aquienllamo.aquienllamo.model.auth.permissions.RolesUser;
import com.aquienllamo.aquienllamo.model.auth.repositories.CredentialsRepository;
import com.aquienllamo.aquienllamo.model.auth.repositories.RoleRepository;
import com.aquienllamo.aquienllamo.model.dtos.Request.AdministradorDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.AdministradorDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.AdministradorEntity;
import com.aquienllamo.aquienllamo.model.exceptions.RoleNotFoundEx;
import com.aquienllamo.aquienllamo.model.mappers.AdministradorMapper;
import com.aquienllamo.aquienllamo.model.repositories.AdministradorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor // utilizo este tipo de constructor porque solo quiero inyectar las dependencias de tipo final
@Transactional
public class AdministradorService {

        private final AdministradorRepository administradorRepository; // para hablar con la bdd
        private final AdministradorMapper administradorMapper;
        private final PasswordEncoder passwordEncoder; // para encriptar las claves.
        private final CredentialsRepository credentialsRepository;
        private final RoleRepository roleRepository;

        // Registrar administrador: encripta la clave antes de guardar y después lo guarda.
        public AdministradorDTOResponse registrar(AdministradorDTORequest admin) {
            String claveEncriptada = passwordEncoder.encode(admin.getClave());
            admin.setClave(claveEncriptada);

            AdministradorEntity administrador=administradorMapper.toEntity(admin);
            administradorRepository.save(administrador);

            RoleEntity rol= roleRepository.findByRole(RolesUser.ROLE_ADMINISTRADOR)
                    .orElseThrow(() -> new RoleNotFoundEx("Rol ADMINISTRADOR no encontrado"));

            CredentialsEntity credencial = CredentialsEntity.builder()
                    .username(admin.getNombreUsuario())
                    .clave(claveEncriptada)
                    .enabled(true)
                    .administrador(administrador)
                    .roles(Set.of(rol))
                    .build();

            credentialsRepository.save(credencial);

            return administradorMapper.toResponse(administrador);
        }


}
