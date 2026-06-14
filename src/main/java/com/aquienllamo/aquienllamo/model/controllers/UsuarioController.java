package com.aquienllamo.aquienllamo.model.controllers;

import com.aquienllamo.aquienllamo.model.dtos.Request.DeleteUsuarioDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Request.LoginUsuarioDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Request.UsuarioDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.UsuarioDTOResponse;
import com.aquienllamo.aquienllamo.model.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/aquienllamo/usuarios")

public class UsuarioController {
    private final UsuarioService usuarioService;
    // iniciar sesión lo moví a AUTH CONTROLLER!!!!!!!!!!
    // buscar el perfil a través del uuid
    @GetMapping("/perfil/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioDTOResponse buscar(@PathVariable String uuid){
        return usuarioService.getByUuid(uuid);
    }

    // actualizar el perfil
    @PutMapping("/actualizar/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioDTOResponse actualizar(@PathVariable String uuid, @ModelAttribute UsuarioDTORequest dto){
        // chicas, si usamos multipartfile para las fotos necesitamos usar modelattribute aparentemente
        return usuarioService.update(uuid, dto);
    }

    // eliminar la cuenta x el momento dejo deleteusuariodtorequest así confirman su clave.
    @DeleteMapping("/eliminar/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public String borrar(@PathVariable String uuid, @Valid @RequestBody DeleteUsuarioDTORequest dto){
        return usuarioService.deleteUser(uuid, dto.getPassword());
    }

    // listar todos users para admins
    @GetMapping("/todos")
    @ResponseStatus(HttpStatus.OK)
    public List<UsuarioDTOResponse> listarTodos() {
        return usuarioService.getAllUsers();
    }
    @GetMapping("/mi-perfil")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioDTOResponse miPerfil( Authentication authentication){
        return usuarioService.getMyProfile(authentication.getName());
    }
}
