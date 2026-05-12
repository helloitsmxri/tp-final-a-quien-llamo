package com.aquienllamo.aquienllamo.model.services;

import com.aquienllamo.aquienllamo.model.entities.AdministradorEntity;
import com.aquienllamo.aquienllamo.model.repositories.AdministradorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // utilizo este tipo de constructor porque solo quiero inyectar las dependencias de tipo final
@Transactional
public class AdministradorService {

        private final AdministradorRepository administradorRepository; // para hablar con la bdd
        private final PasswordEncoder passwordEncoder; // para encriptar las claves.

        // Registrar administrador: encripta la clave antes de guardar y después lo guarda.
        public AdministradorEntity registrar(AdministradorEntity admin) {
            String claveEncriptada = passwordEncoder.encode(admin.getClave());
            admin.setClave(claveEncriptada);
            return administradorRepository.save(admin);
        }

        // Login:
        public AdministradorEntity login(String nombreUsuario, String claveIngresada) {
            AdministradorEntity admin = administradorRepository
                    .findByNombreUsuario(nombreUsuario) // busca el admin y compara la clave
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            // el matches() me compara la clave ingresada con la encriptada
            if (!passwordEncoder.matches(claveIngresada, admin.getClave())) {
                throw new RuntimeException("Clave incorrecta");
            }

            return admin;
        }
}
