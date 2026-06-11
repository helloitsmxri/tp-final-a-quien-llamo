package com.aquienllamo.aquienllamo.model.auth.controllers;

import com.aquienllamo.aquienllamo.model.auth.Credentials.CredentialsEntity;
import com.aquienllamo.aquienllamo.model.auth.JWT.JwtService;
import com.aquienllamo.aquienllamo.model.auth.securityDtos.AuthRequest;
import com.aquienllamo.aquienllamo.model.auth.securityDtos.AuthResponse;
import com.aquienllamo.aquienllamo.model.auth.securityDtos.RefreshTokenRequest;
import com.aquienllamo.aquienllamo.model.auth.securityServices.AuthService;
import com.aquienllamo.aquienllamo.model.dtos.Request.UsuarioDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.UsuarioDTOResponse;
import com.aquienllamo.aquienllamo.model.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UsuarioService userService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse iniciarSesion(@Valid @RequestBody AuthRequest request){
        return authService.authenticate(request);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDTOResponse registrarse(@Valid @RequestBody UsuarioDTORequest request){
        return userService.createUser(request);
    }

    @PostMapping("/refresh")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse refreshToken(@Valid @RequestBody RefreshTokenRequest request){
        AuthResponse response=authService.refreshAccessToken(request.refreshToken());
        return response;
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(@RequestBody RefreshTokenRequest request){
        authService.logout(request.refreshToken());
    }

}
