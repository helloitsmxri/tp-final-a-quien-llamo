package com.aquienllamo.aquienllamo.model.controllers;

import com.aquienllamo.aquienllamo.model.dtos.Request.usuario.DeleteUsuarioDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Request.usuario.UsuarioUpdateDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.UsuarioDTOResponse;
import com.aquienllamo.aquienllamo.model.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PatchMapping("/actualizar/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioDTOResponse actualizar(@PathVariable String uuid, @ModelAttribute UsuarioUpdateDTORequest dto){
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
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @ResponseStatus(HttpStatus.OK)
    public List<UsuarioDTOResponse> listarTodos() {
        return usuarioService.getAllUsers();
    }

    // ver MI perfil
    @GetMapping("/mi-perfil")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioDTOResponse miPerfil( Authentication authentication){
        return usuarioService.getMyProfile(authentication.getName());
    }

    // amonestar USUARIO
    @PatchMapping("/amonestar/{uuid}")
    @PreAuthorize("hasRole('ADMINISTRADOR')") // para que no entre cualquiera
    @ResponseStatus(HttpStatus.OK)
    public String amonestar(@PathVariable String uuid, @RequestParam String motivo){
        return usuarioService.amonestarUsuario(uuid, motivo);
    }

    // dar de baja usuario
    @PatchMapping("/baja/{uuid}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @ResponseStatus(HttpStatus.OK)
    public String darDeBaja(@PathVariable String uuid){
        return usuarioService.darDeBajaUsuario(uuid);
    }

    //lo podría hacer con request param
    // encontrar usuario por documento
    @GetMapping("/usuario/dni/{dni}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioDTOResponse mostrarPorDocumento(@PathVariable String dni){
        return usuarioService.getByDni(dni);
    }

    // encontrar usuario por email
    @GetMapping("/usuario/email/{email}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioDTOResponse mostrarPorCorreo(@PathVariable String email){
        return usuarioService.getByEmail(email);
    }

    // quitar suspensión
    @PatchMapping("/quitar-suspension/{uuid}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @ResponseStatus(HttpStatus.OK)
    public String quitarSuspension(@PathVariable String uuid){
        return usuarioService.quitarSuspension(uuid);
    }

    // quitar amonestación
    @PatchMapping("/quitar-baja/{uuid}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @ResponseStatus(HttpStatus.OK)
    public String quitarBaja(@PathVariable String uuid){
        return usuarioService.quitarBaja(uuid);
    }

}
