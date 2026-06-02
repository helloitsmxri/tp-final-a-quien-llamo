package com.aquienllamo.aquienllamo.model.auth.securityServices;

import com.aquienllamo.aquienllamo.model.auth.securityDtos.AuthRequest;
import com.aquienllamo.aquienllamo.model.repositories.CredentialsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService{ //servicio para la autentificación inicial del usuario.
        private final CredentialsRepository credentialsRepository;
        private final AuthenticationManager authenticationManager;

    public UserDetails authenticate(AuthRequest input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.username(),
                        input.password()
                )
        );
        return
                credentialsRepository.findByUsername(input.username()).orElseThrow(
                        () -> new UsernameNotFoundException("Usuario no encontrado")
                        );
    }
    }
