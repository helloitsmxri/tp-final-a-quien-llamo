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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/aquienllamo/trabajos")
public class TrabajoController {

    private final TrabajoService trabajoService;

    // =====================================================
    // CREAR TRABAJO
    // =====================================================

    @PostMapping
    @PreAuthorize("hasRole('TECNICO')")
    public ResponseEntity<TrabajoDTOResponse> crearTrabajo(
            @Valid @RequestBody TrabajoDTORequest trabajoDTORequest) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(trabajoService.crearTrabajo(trabajoDTORequest));
    }

    // =====================================================
    // LISTAR TODOS LOS TRABAJOS
    // =====================================================

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'SUPERADMINISTRADOR')")
    public ResponseEntity<List<TrabajoDTOResponse>> listarTrabajos() {

        return ResponseEntity.ok(trabajoService.listarTrabajos());
    }

    // =====================================================
    // LISTAR MIS TRABAJOS
    // =====================================================

    @GetMapping("/mis-trabajos")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<TrabajoDTOResponse>> listarMisTrabajos() {

        return ResponseEntity.ok(
                trabajoService.listarMisTrabajos());
    }

    // =====================================================
    // LISTAR TRABAJOS POR ESTADO
    // =====================================================

    @GetMapping("/listarTrabajos/{estadoTrabajo}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'SUPERADMINISTRADOR')")
    public ResponseEntity<List<TrabajoDTOResponse>> listarTrabajosPorEstadoTrabajo(
            @PathVariable EstadoTrabajo estadoTrabajo) {

        return ResponseEntity.ok(
                trabajoService.listarTrabajoPorEstadoTrabajo(estadoTrabajo));
    }

    // =====================================================
    // CAMBIAR ESTADO DEL TRABAJO
    // =====================================================

    @PatchMapping("/{uuid}/estado")
    @PreAuthorize("hasAnyRole('TECNICO', 'ADMINISTRADOR', 'SUPERADMINISTRADOR')")
    public ResponseEntity<TrabajoDTOResponse> cambiarEstado(
            @PathVariable String uuid,
            @RequestParam EstadoTrabajo estadoTrabajo) {

        return ResponseEntity.ok(
                trabajoService.cambiarEstadoTrabajo(uuid, estadoTrabajo));
    }

    // =====================================================
    // LISTAR TRABAJOS POR CLIENTE
    // =====================================================

    @GetMapping("/cliente/{uuid}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'SUPERADMINISTRADOR')"
            + " or @trabajoService.perteneceAlUsuario(#uuid)")
    public ResponseEntity<List<TrabajoDTOResponse>> listarTrabajoPorCliente(
            @PathVariable String uuid) {

        return ResponseEntity.ok(
                trabajoService.listarTrabajosPorCliente(uuid));
    }

    // =====================================================
    // LISTAR TRABAJOS POR TÉCNICO
    // =====================================================

    @GetMapping("/tecnico/{uuid}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'SUPERADMINISTRADOR')"
            + " or (hasRole('TECNICO') and @trabajoService.perteneceAlUsuario(#uuid))")
    public ResponseEntity<List<TrabajoDTOResponse>> listarTrabajoPorTecnico(
            @PathVariable String uuid) {

        return ResponseEntity.ok(
                trabajoService.listarTrabajosPorTecnico(uuid));
    }

    // =====================================================
    // OBTENER TRABAJO POR UUID
    // =====================================================

    @GetMapping("/{uuid}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<TrabajoDTOResponse> obtenerTrabajo(
            @PathVariable String uuid) {

        return ResponseEntity.ok(
                trabajoService.obtenerTrabajoPorUuid(uuid));
    }
}