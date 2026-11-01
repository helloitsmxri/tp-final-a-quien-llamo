package com.aquienllamo.aquienllamo.entidades.usuario.details;

import com.aquienllamo.aquienllamo.auth.repositories.CredentialsRepository;
import com.aquienllamo.aquienllamo.entidades.tecnico.repository.TecnicoRepository;
import com.aquienllamo.aquienllamo.entidades.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UsuarioDetails implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final TecnicoRepository tecnicoRepository;

    private final CredentialsRepository credentialsRepository; // ← cambiás esto

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return credentialsRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
    }
}
