package com.aquienllamo.aquienllamo.model.controllers;

import com.aquienllamo.aquienllamo.model.dtos.Request.HabilidadDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.habilidad.HabilidadCreadaDTOResponse;
import com.aquienllamo.aquienllamo.model.dtos.Response.habilidad.HabilidadDTOResponse;
import com.aquienllamo.aquienllamo.model.services.HabilidadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/aquienllamo/habilidades")
@RestController
@RequiredArgsConstructor
public class HabilidadController {
    private final HabilidadService habilidadService;

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','USUARIO','TECNICO')")
    @GetMapping("/listar")
    public ResponseEntity<List<HabilidadDTOResponse>> listarHabilidades(){
        return ResponseEntity.ok(habilidadService.listarHabilidades());
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<HabilidadCreadaDTOResponse> crearHabilidad(@Valid @RequestBody HabilidadDTORequest habilidad){
        return ResponseEntity.status(HttpStatus.CREATED).body(habilidadService.crearHabilidad(habilidad));
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PutMapping("/actualizar/{uuid}")
    public ResponseEntity<HabilidadDTOResponse> actualizarHabilidad(@PathVariable String uuid, @Valid @RequestBody HabilidadDTORequest habilidad){
        return ResponseEntity.ok(habilidadService.actualizarHabilidad(uuid, habilidad));
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/eliminar/{uuid}")
    public ResponseEntity<Void> eliminarHabilidad(@PathVariable String uuid){
        habilidadService.eliminarHabilidad(uuid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','USUARIO','TECNICO')")
    @GetMapping("/{uuid}")
    public ResponseEntity<HabilidadDTOResponse> buscarPorUuid(@PathVariable String uuid){
        return ResponseEntity.ok(habilidadService.buscarPorUuid(uuid));
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','USUARIO','TECNICO')")
    @GetMapping("/buscar")
    public ResponseEntity<List<HabilidadDTOResponse>> buscarPorNombre(@RequestParam String nombre){
        return ResponseEntity.ok(habilidadService.buscarPorNombre(nombre));
    }
}
