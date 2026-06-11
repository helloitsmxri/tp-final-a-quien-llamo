package com.aquienllamo.aquienllamo.model.auth.securityServices;

import com.aquienllamo.aquienllamo.model.auth.Credentials.CredentialsEntity;
import com.aquienllamo.aquienllamo.model.auth.JWT.JwtService;
import com.aquienllamo.aquienllamo.model.auth.securityDtos.AuthRequest;
import com.aquienllamo.aquienllamo.model.auth.securityDtos.AuthResponse;
import com.aquienllamo.aquienllamo.model.repositories.CredentialsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class AuthService{ //servicio para la autentificación inicial del usuario.
        private final CredentialsRepository credentialsRepository;
        private final AuthenticationManager authenticationManager;
        private final JwtService jwtService;

    public AuthResponse authenticate(AuthRequest input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.username(),
                        input.password()
                )
        );

        CredentialsEntity entity=credentialsRepository.findByUsername(input.username())
                .orElseThrow(() ->
                        new UsernameNotFoundException("Usuario no encontrado"));
        String accessToken=jwtService.generateToken(entity);
        String refreshToken=jwtService.generateRefreshToken(entity);
        entity.setRefreshToken(refreshToken);
        credentialsRepository.save(entity);
        return new AuthResponse(accessToken, refreshToken);
    }

    @Transactional
    public AuthResponse refreshAccessToken(String refreshToken) {

        String username = jwtService.extractUsername(refreshToken);

        CredentialsEntity user = credentialsRepository.findByUsername(username)
                .orElseThrow(() ->
                        new IllegalArgumentException("No se encontró el usuario"));

        if (!user.getRefreshToken().equals(refreshToken)) {
            throw new IllegalArgumentException("El refresh token no coincide");
        }

        if (!jwtService.validateRefreshToken(refreshToken, user)) {
            throw new IllegalArgumentException("El token es inválido o expiró");
        }

        String newAccessToken = jwtService.generateToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);

        user.setRefreshToken(newRefreshToken);
        credentialsRepository.save(user);

        return new AuthResponse(newAccessToken, newRefreshToken);
    }
}
