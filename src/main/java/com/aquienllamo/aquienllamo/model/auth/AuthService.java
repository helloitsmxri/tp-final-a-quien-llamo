package com.aquienllamo.aquienllamo.model.auth;

import com.aquienllamo.aquienllamo.model.auth.credentials.CredentialsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
// necesario declarar AUTH DTO REQUEST Y AUTH BEAN.
public class AuthService {
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
                        () -> new UsernameNotFoundException("Usuario no
                                encontrado")
                        );
    }
}
