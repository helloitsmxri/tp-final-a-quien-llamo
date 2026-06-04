package com.aquienllamo.aquienllamo.model.auth.exceptions;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.*;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.security.core.AuthenticationException;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class RestAuthenticateEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        String errorMessage=switch (authException){
            case BadCredentialsException bde-> "Credenciales invalidas";
            case DisabledException de-> "Cuenta deshabilitada";
            case LockedException le-> "Cuenta bloquedad";
            case AccountExpiredException ae->"Cuenta expirada";
            case CredentialsExpiredException cee-> "Credenciales expiradas";
            case InsufficientAuthenticationException ie -> "Autenticación insuficiente";
            case AuthenticationServiceException ae -> "Error en el servicio de autenticación";
            default -> "Error de autenticación: "+ authException.getMessage();
        };

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("error", errorMessage);
        responseData.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        responseData.put("path", request.getRequestURI()); // ¿por qué uri y no url?

        response.getWriter().write(mapper.writeValueAsString(responseData));
        response.getWriter().flush();
    }


}
