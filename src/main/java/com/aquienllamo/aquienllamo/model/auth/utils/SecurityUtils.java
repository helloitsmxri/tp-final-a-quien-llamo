package com.aquienllamo.aquienllamo.model.auth.utils;
import com.aquienllamo.aquienllamo.model.auth.credentials.CredentialsEntity;
import com.aquienllamo.aquienllamo.model.entities.UsuarioEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    private SecurityUtils() {
    }

    public static Authentication getAuthentication() {
        return SecurityContextHolder
                .getContext()
                .getAuthentication();
    }

    public static CredentialsEntity getCurrentCredentials() {

        Authentication authentication = getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated()) {
            throw new RuntimeException("Usuario no autenticado");
        }

        return (CredentialsEntity) authentication.getPrincipal();
    }

    public static UsuarioEntity getCurrentUser() {
        return getCurrentCredentials().getUsuario();
    }

    public static Long getCurrentUserId() {
        return getCurrentUser().getIdUsuario().longValue();
    }
}
