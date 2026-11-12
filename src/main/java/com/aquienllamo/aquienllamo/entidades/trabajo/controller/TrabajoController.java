package com.aquienllamo.aquienllamo.entidades.trabajo.controller;

import com.aquienllamo.aquienllamo.entidades.trabajo.dto.request.TrabajoDTORequest;
import com.aquienllamo.aquienllamo.entidades.trabajo.dto.response.TrabajoDTOResponse;
import com.aquienllamo.aquienllamo.entidades.trabajo.service.TrabajoService;
import com.aquienllamo.aquienllamo.entidades.trabajo.enums.EstadoTrabajo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/aquienllamo/trabajos")
public class TrabajoController {

    private final TrabajoService trabajoService;

    //crear un trabajo (solo técnico; el service verifica que sea el dueño del presupuesto)
    @PostMapping
    @PreAuthorize("hasRole('TECNICO')")
    public ResponseEntity<TrabajoDTOResponse> crearTrabajo (@Valid @RequestBody TrabajoDTORequest trabajoDTORequest,
                                                            Authentication authentication){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(trabajoService.crearTrabajo(trabajoDTORequest, authentication.getName()));
    }

    //enlistar TODOS los trabajos (solo admin)
    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<TrabajoDTOResponse>> listarTrabajos(){
        return ResponseEntity.ok().body(trabajoService.listarTrabajos());
    }

    // NUEVO: mis trabajos (como cliente o como técnico)
    @GetMapping("/mis-trabajos")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<TrabajoDTOResponse>> listarMisTrabajos(Authentication authentication){
        return ResponseEntity.ok(trabajoService.listarMisTrabajos(authentication.getName()));
    }

    //listar los trabajos segun su estado (solo admin)
    // OJO: en la URL el estado va como En_proceso (con guion bajo)
    @GetMapping("/listarTrabajos/{estadoTrabajo}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<TrabajoDTOResponse>> listarTrabajosPorEstadoTrabajo(@PathVariable EstadoTrabajo estadoTrabajo){
        return ResponseEntity.ok().body(trabajoService.listarTrabajoPorEstadoTrabajo(estadoTrabajo));
    }

    //cambiar estado de un trabajo (OJO: En_proceso con guion bajo)
    @PatchMapping("/{uuid}/estado")
    @PreAuthorize("hasRole('TECNICO') or hasRole('ADMINISTRADOR')")
    public ResponseEntity<TrabajoDTOResponse> cambiarEstado (@PathVariable String uuid,
                                                             @RequestParam EstadoTrabajo estadoTrabajo,
                                                             Authentication authentication){
        return ResponseEntity.ok(trabajoService.cambiarEstadoTrabajo(
                uuid, estadoTrabajo, authentication.getName(), esAdmin(authentication)));
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

    @GetMapping("/{uuid}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<TrabajoDTOResponse> obtenerTrabajo(@PathVariable String uuid,
                                                             Authentication authentication){
        return ResponseEntity.ok(trabajoService.obtenerTrabajoPorUuid(
                uuid, authentication.getName(), esAdmin(authentication)));
    }

    private boolean esAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMINISTRADOR"));
    }
}