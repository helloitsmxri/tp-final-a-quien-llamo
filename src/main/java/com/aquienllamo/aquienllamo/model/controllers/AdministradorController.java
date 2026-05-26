package com.aquienllamo.aquienllamo.model.controllers;

import com.aquienllamo.aquienllamo.model.dtos.Request.AdministradorDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.AdministradorDTOResponse;
import com.aquienllamo.aquienllamo.model.services.AdministradorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aquienllamo/administradores")
@RequiredArgsConstructor
public class AdministradorController {

    private final AdministradorService administradorService;

    //registrar administrador
    @PostMapping("/registrar")
    public ResponseEntity<AdministradorDTOResponse> registrar(@Valid @RequestBody AdministradorDTORequest dto)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(administradorService.registrar(dto));
    }

    //logear administrador:
    @PostMapping("login")
    public ResponseEntity<AdministradorDTOResponse> login(@RequestBody AdministradorDTORequest dto)
    {
        return ResponseEntity.ok().body(administradorService.login(dto.getNombreUsuario(),dto.getClave()));
    }
}
