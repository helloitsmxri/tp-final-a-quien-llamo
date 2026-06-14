package com.aquienllamo.aquienllamo.model.auth.securityServices;

import com.aquienllamo.aquienllamo.model.auth.Credentials.CredentialsEntity;
import com.aquienllamo.aquienllamo.model.auth.JWT.JwtService;
import com.aquienllamo.aquienllamo.model.auth.securityDtos.AuthRequest;
import com.aquienllamo.aquienllamo.model.auth.securityDtos.AuthResponse;
import com.aquienllamo.aquienllamo.model.auth.repositories.CredentialsRepository;
import com.aquienllamo.aquienllamo.model.auth.securityDtos.request.ChangePasswordRequest;
import com.aquienllamo.aquienllamo.model.auth.securityDtos.request.ForgotPasswordDTORequest;
import com.aquienllamo.aquienllamo.model.auth.securityDtos.request.ResetPasswordDTORequest;
import com.aquienllamo.aquienllamo.model.entities.UsuarioEntity;
import com.aquienllamo.aquienllamo.model.exceptions.DisabledProfileEx;
import com.aquienllamo.aquienllamo.model.exceptions.InvalidPasswordEx;
import com.aquienllamo.aquienllamo.model.exceptions.UserSuspendedException;
import io.jsonwebtoken.JwtException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import javax.management.JMException;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor

public class AuthService{ //servicio para la autentificación inicial del usuario.
        private final CredentialsRepository credentialsRepository;
        private final AuthenticationManager authenticationManager;
        private final JwtService jwtService;
        private final PasswordEncoder passwordEncoder;

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

        UsuarioEntity usuario = entity.getUsuario();

        // cuenta dada de baja
        if (Boolean.FALSE.equals(usuario.getActivo())){
            throw new DisabledProfileEx("La cuenta se encuentra dada de baja");
        }

        // suspensión temporal
        if(usuario.getFechaFinSuspension() != null && usuario.getFechaFinSuspension().isAfter(LocalDate.now())){
            throw new UserSuspendedException("Usuario suspendido hasta " +usuario.getFechaFinSuspension());
        }

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


    // Salirse de la sesión
    @Transactional
    public void logout(String refreshToken){
        CredentialsEntity user = credentialsRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("Token no válido"));

        user.setRefreshToken(null);
        credentialsRepository.save(user);
    }

    // cambiar password si ya me la sé
    @Transactional
    public AuthResponse changePassword(String username, ChangePasswordRequest req){
        // buscamos q la credencial exista
        CredentialsEntity cred = credentialsRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // me fijo si las claves coinciden
        if (!passwordEncoder.matches(req.getCurrentPassword(), cred.getClave())){
            throw new InvalidPasswordEx("La contraseña introducida no es correcta.");
        }

        // si coincidieron, acepto la nueva:
        String nuevaClave = passwordEncoder.encode(req.getNewPassword());
        cred.setClave(nuevaClave);

        if (cred.getUsuario()!=null){
            cred.getUsuario().setClave(nuevaClave);
        }

        // a la credencial le genero su nuevo accestoken y su refresh
        String accesToken = jwtService.generateToken(cred);
        String refreshToken = jwtService.generateRefreshToken(cred);

        cred.setRefreshToken(refreshToken);
        credentialsRepository.save(cred);

        return new AuthResponse(accesToken, refreshToken);
    }

    public void forgotPassword(ForgotPasswordDTORequest req){
        CredentialsEntity cred = credentialsRepository.findByUsername(req.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("No se encontró el usuario en el sistema."));

        String token = jwtService.generatePasswordResetToken(cred);

        // acá va lo de enviar correo pau
    }

    @Transactional
    public void resetPassword(ResetPasswordDTORequest req){
        String usuario = jwtService.extractUsername(req.getToken());

        CredentialsEntity cred = credentialsRepository.findByUsername(usuario)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        boolean val = jwtService.validatePasswordResetToken(req.getToken(), cred);
        if (!val){
            throw new JwtException("Token inválido o expirado");
        }

        String nuevaClave = passwordEncoder.encode(req.getNewPassword());
        cred.setClave(nuevaClave);

        if (cred.getUsuario() != null){
            cred.getUsuario().setClave(nuevaClave);
        }

        cred.setRefreshToken(null);

        credentialsRepository.save(cred);
    }
}
