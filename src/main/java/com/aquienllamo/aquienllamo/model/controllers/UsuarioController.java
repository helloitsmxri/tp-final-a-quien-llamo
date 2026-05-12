package com.aquienllamo.aquienllamo.model.controllers;

import com.aquienllamo.aquienllamo.model.dtos.Request.DeleteUsuarioDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Request.LoginUsuarioDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Request.UsuarioDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.UsuarioDTOResponse;
import com.aquienllamo.aquienllamo.model.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/aquienllamo/usuarios")

public class UsuarioController {
    private final UsuarioService usuarioService;

    // creación
    @PostMapping("/sign-up")
    public ResponseEntity<UsuarioDTOResponse> registrar(@Valid @RequestBody UsuarioDTORequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.createUser(dto));
    }

    // login
    @PostMapping("/login")
    public ResponseEntity<UsuarioDTOResponse> ingresar(@Valid @RequestBody LoginUsuarioDTORequest dto){
        //devuelve el tipo de usuario solo
        return ResponseEntity.ok(usuarioService.login(dto.getEmail(), dto.getPassword()));
    }

    // buscar el perfil a través del uuid
    @GetMapping("/perfil/{uuid}")
    public ResponseEntity<UsuarioDTOResponse> buscar(@PathVariable String uuid){
        return ResponseEntity.ok(usuarioService.getByUuid(uuid));
    }

    // actualizar el perfil
    @PutMapping("/actualizar/{uuid}")
    public ResponseEntity<UsuarioDTOResponse> actualizar(@PathVariable String uuid, @ModelAttribute UsuarioDTORequest dto){
        // chicas, si usamos multipartfile para las fotos necesitamos usar modelattribute aparentemente
        return ResponseEntity.ok(usuarioService.update(uuid, dto));
    }

    // eliminar la cuenta
    @DeleteMapping("/eliminar/{uuid}")
    public ResponseEntity<String> borrar(@PathVariable String uuid, @Valid @RequestBody DeleteUsuarioDTORequest dto){
        return ResponseEntity.ok(usuarioService.deleteUser(uuid, dto.getPassword()));
    }

    // listar todos users para admins
    @GetMapping("/todos")
    public ResponseEntity<List<UsuarioDTOResponse>> listarTodos() {
        return ResponseEntity.ok(usuarioService.getAllUsers());
    }
}
