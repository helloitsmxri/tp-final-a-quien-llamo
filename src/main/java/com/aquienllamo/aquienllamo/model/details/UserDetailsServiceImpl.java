package com.aquienllamo.aquienllamo.model.details;

import com.aquienllamo.aquienllamo.model.auth.repositories.CredentialsRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService { //UserDetailsService: es una interfaz de JAVA.
    private final CredentialsRepository credentialsRepository;

    // para cargar los datos del usuario desde la base de datos.
    // con UserDetailsService le decimos a Spring cómo obtener la información de los usuarios mediante JPA.
    @Override
    public UserDetails loadUserByUsername(@NonNull String username)
            throws UsernameNotFoundException {
        return
                credentialsRepository.findByUsername(username).orElseThrow(() -> new
                        UsernameNotFoundException("User not found"));
    }
}
