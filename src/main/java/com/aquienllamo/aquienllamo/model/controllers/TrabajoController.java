package com.aquienllamo.aquienllamo.model.controllers;

import com.aquienllamo.aquienllamo.model.Enum.EstadoTrabajo;
import com.aquienllamo.aquienllamo.model.dtos.Request.TrabajoDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.TrabajoDTOResponse;
import com.aquienllamo.aquienllamo.model.services.TrabajoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/aquienllamo/trabajos")
public class TrabajoController {

    private final TrabajoService trabajoService;

    //crear un trabajo
    @PostMapping
    public ResponseEntity<TrabajoDTOResponse> crearTrabajo (@Valid @RequestBody TrabajoDTORequest trabajoDTORequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(trabajoService.crearTrabajo(trabajoDTORequest));
    }

    //enlistar los trabajos
    @GetMapping
    public ResponseEntity<List<TrabajoDTOResponse>> listarTrabajos(){
        return ResponseEntity.ok().body(trabajoService.listarTrabajos());
    }

    //listar los trabajos segun su estado
    @GetMapping("/listarTrabajos/{estadoTrabajo}")
    public ResponseEntity<List<TrabajoDTOResponse>> listarTrabajosPorEstadoTrabajo(@PathVariable EstadoTrabajo estadoTrabajo){
        return ResponseEntity.ok().body(trabajoService.listarTrabajoPorEstadoTrabajo(estadoTrabajo));
    }

    //cambiar estado de un trabajo
    @PatchMapping("/{uuid}/estado")
    @PreAuthorize("hasRole('TECNICO') or hasRole('ADMINISTRADOR')")
    public ResponseEntity<TrabajoDTOResponse> cambiarEstado (@PathVariable String uuid, @RequestParam EstadoTrabajo estadoTrabajo){
        return ResponseEntity.ok(trabajoService.cambiarEstadoTrabajo(uuid, estadoTrabajo));
    }

    //listar trabajos por cliente
    @GetMapping("/cliente/{uuid}")
    @PreAuthorize("hasRole('ADMINISTRADOR') or @trabajoService.perteneceAlUsuario(#uuid, authentication.name)")
    public ResponseEntity<List<TrabajoDTOResponse>> listarTrabajoPorCliente(@PathVariable String uuid){
        return ResponseEntity.ok(trabajoService.listarTrabajosPorCliente(uuid));
    }

    @GetMapping("/tecnico/{uuid}")
    @PreAuthorize("hasRole('ADMINISTRADOR') or @trabajoService.perteneceAlUsuario(#uuid, authentication.name)")
    public ResponseEntity<List<TrabajoDTOResponse>> listarTrabajoPorTecnico(@PathVariable String uuid){
        return ResponseEntity.ok(trabajoService.listarTrabajosPorTecnico(uuid));
    }
}
