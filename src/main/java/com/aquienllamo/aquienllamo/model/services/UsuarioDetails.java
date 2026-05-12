package com.aquienllamo.aquienllamo.model.services;

import com.aquienllamo.aquienllamo.model.details.UsuarioSecurity;
import com.aquienllamo.aquienllamo.model.entities.TecnicoEntity;
import com.aquienllamo.aquienllamo.model.entities.UsuarioEntity;
import com.aquienllamo.aquienllamo.model.repositories.TecnicoRepository;
import com.aquienllamo.aquienllamo.model.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UsuarioDetails implements UserDetails {

    private final UsuarioRepository usuarioRepository;
    private final TecnicoRepository tecnicoRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UsuarioEntity user = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email no encontrado: " + email));

        List<SimpleGrantedAuthority> permisos = new ArrayList<>();
        permisos.add(new SimpleGrantedAuthority("ROLE_USER"));

        // si está en la tabla de técnicos le doy el rol técnico.
        if (tecnicoRepository.existsByUsuarioUuid(user.getUuid())) {
            permisos.add(new SimpleGrantedAuthority("ROLE_TECNICO"));
        }

        return new UsuarioSecurity(user, permisos);
    }
}
