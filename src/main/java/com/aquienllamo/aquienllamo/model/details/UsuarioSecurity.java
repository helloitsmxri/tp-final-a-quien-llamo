package com.aquienllamo.aquienllamo.model.details;


import com.aquienllamo.aquienllamo.model.entities.UsuarioEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@RequiredArgsConstructor
public class UsuarioSecurity implements UserDetails {

    private final UsuarioEntity usuario;
    private final Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities; // devolver lo q recibimos
    }

    @Override
    public String getPassword() {
        return usuario.getClave();
    }

    @Override
    public String getUsername() { // no tenemos username pero tenemos correo y lo toma como eso para el inicio sesión
        return usuario.getEmail();
    }

    // Cambiamos a true para que Spring permita el acceso
    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }

    public String getUuid() {
        return usuario.getUuid();
    }
}
