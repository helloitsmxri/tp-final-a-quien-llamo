package com.aquienllamo.aquienllamo.model.services;

import com.aquienllamo.aquienllamo.model.auth.JWT.JwtService;
import com.aquienllamo.aquienllamo.model.dtos.Request.AdministradorDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.AdministradorDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.AdministradorEntity;
import com.aquienllamo.aquienllamo.model.mappers.AdministradorMapper;
import com.aquienllamo.aquienllamo.model.repositories.AdministradorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // utilizo este tipo de constructor porque solo quiero inyectar las dependencias de tipo final
@Transactional
public class AdministradorService {

        private final AdministradorRepository administradorRepository; // para hablar con la bdd
        private final AdministradorMapper administradorMapper;
        private final PasswordEncoder passwordEncoder; // para encriptar las claves.
        private final JwtService jwtService;

        // Registrar administrador: encripta la clave antes de guardar y después lo guarda.
        public AdministradorDTOResponse registrar(AdministradorDTORequest admin) {
            String claveEncriptada = passwordEncoder.encode(admin.getClave());
            admin.setClave(claveEncriptada);

            AdministradorEntity administrador=administradorMapper.toEntity(admin);
            return administradorMapper.toResponse(administradorRepository.save(administrador));
        }

        // Login:
        public AdministradorDTOResponse login(String nombreUsuario, String claveIngresada) {
            AdministradorEntity admin = administradorRepository
                    .findByNombreUsuario(nombreUsuario) // busca el admin y compara la clave
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            // el matches() me compara la clave ingresada con la encriptada
            if (!passwordEncoder.matches(claveIngresada, admin.getClave())) {
                throw new RuntimeException("Clave incorrecta");
            }
            UserDetails userD = User.builder()
                    .username(admin.getNombreUsuario())
                    .password(admin.getClave())
                    .authorities("ROLE_ADMIN")
                    .build();

            String token = jwtService.generateToken(userD);

            return AdministradorDTOResponse.builder()
                    .nombreUsuario(admin.getNombreUsuario())
                    .token(token)
                    .build();
        }
}
