package com.aquienllamo.aquienllamo.model.controllers;

import com.aquienllamo.aquienllamo.model.dtos.Request.AsignRolDTORequest;
import com.aquienllamo.aquienllamo.model.services.AdministradorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aquienllamo/administradores")
@RequiredArgsConstructor
public class AdministradorController {

    private final AdministradorService administradorService;


    @PostMapping("/changeRol")
    @PreAuthorize("hasRole('SUPERADMINISTRADOR')")
    @ResponseStatus(HttpStatus.OK)
    public String registrarAdmin(@Valid @RequestBody AsignRolDTORequest dto)
    {
        return administradorService.asigneRol(dto);
    }

    // amonestar USUARIO
    @PatchMapping("/remoRolAdmin")
    @PreAuthorize("hasRole('ADMINISTRADOR')") // para que no entre cualquiera que no sea SuperAdministrador
    @ResponseStatus(HttpStatus.OK)
    public String removeRolAdmin(@PathVariable String uuid){
        return administradorService.removeRolAdmin(uuid);
    }
}
