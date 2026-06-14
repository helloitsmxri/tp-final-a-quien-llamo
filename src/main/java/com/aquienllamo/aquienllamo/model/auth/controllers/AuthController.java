package com.aquienllamo.aquienllamo.model.auth.controllers;

import com.aquienllamo.aquienllamo.model.auth.securityDtos.AuthRequest;
import com.aquienllamo.aquienllamo.model.auth.securityDtos.AuthResponse;
import com.aquienllamo.aquienllamo.model.auth.securityDtos.request.ChangePasswordRequest;
import com.aquienllamo.aquienllamo.model.auth.securityDtos.RefreshTokenRequest;
import com.aquienllamo.aquienllamo.model.auth.securityDtos.request.ForgotPasswordDTORequest;
import com.aquienllamo.aquienllamo.model.auth.securityDtos.request.ResetPasswordDTORequest;
import com.aquienllamo.aquienllamo.model.auth.securityServices.AuthService;
import com.aquienllamo.aquienllamo.model.dtos.Request.UsuarioDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.UsuarioDTOResponse;
import com.aquienllamo.aquienllamo.model.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
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

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDTOResponse registrarse(@Valid @ModelAttribute UsuarioDTORequest request){ // pa q  no tire error
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


    // cambiar la clave si ya se la sabe:
    @PostMapping("/change-password")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse changePassword(Authentication auth, @RequestBody ChangePasswordRequest request){
        return authService.changePassword(auth.getName(), request);
    }

    // cambiar la clave si me la olvidé
    @PostMapping("/forgot-password")
    @ResponseStatus(HttpStatus.OK)
    public void forgotPassword(@RequestBody ForgotPasswordDTORequest req){
        authService.forgotPassword(req);
    }

    @PostMapping("/reset-password")
    @ResponseStatus(HttpStatus.OK)
    public void resetPassword(@RequestBody ResetPasswordDTORequest req){
        authService.resetPassword(req);
    }

}
