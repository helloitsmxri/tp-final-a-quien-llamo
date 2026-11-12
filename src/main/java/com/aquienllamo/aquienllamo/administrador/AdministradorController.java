package com.aquienllamo.aquienllamo.administrador;

import com.aquienllamo.aquienllamo.administrador.dto.request.AsignRolDTORequest;
import com.aquienllamo.aquienllamo.administrador.dto.response.AdministradorDTOResponse;
import com.aquienllamo.aquienllamo.entidades.usuario.entities.UsuarioEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // quitar rol a usuario
    @PatchMapping("/removeRolAdmin")
    @PreAuthorize("hasRole('SUPERADMINISTRADOR')") // para que no entre cualquiera que no sea SuperAdministrador
    @ResponseStatus(HttpStatus.OK)
    public String removeRolAdmin(@PathVariable String uuid){
        return administradorService.removeRolAdmin(uuid);
    }

    // amonestar USUARIO
    @PatchMapping("/amonestar/{uuid}")
    @PreAuthorize("hasRole('ADMINISTRADOR')") // para que no entre cualquiera que no sea administrador
    @ResponseStatus(HttpStatus.OK)
    public String amonestar(@PathVariable String uuid, @RequestParam String motivo){
        return administradorService.amonestarUsuario(uuid, motivo);
    }

    // dar de baja usuario
    @PatchMapping("/baja/{uuid}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @ResponseStatus(HttpStatus.OK)
    public String darDeBaja(@PathVariable String uuid){
        return administradorService.darDeBajaUsuario(uuid);
    }

    // quitar suspensión
    @PatchMapping("/quitar-suspension/{uuid}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @ResponseStatus(HttpStatus.OK)
    public String quitarSuspension(@PathVariable String uuid){
        return administradorService.quitarSuspension(uuid);
    }

    // quitar amonestación
    @PatchMapping("/quitar-baja/{uuid}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @ResponseStatus(HttpStatus.OK)
    public String quitarBaja(@PathVariable String uuid){
        return administradorService.quitarBaja(uuid);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'SUPERADMINISTRADOR')")
    public ResponseEntity<List<AdministradorDTOResponse>> obtenerAdministradores() {
        return ResponseEntity.ok(administradorService.obtenerAdministradores());
    }
}

