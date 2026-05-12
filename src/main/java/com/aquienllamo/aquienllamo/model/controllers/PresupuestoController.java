package com.aquienllamo.aquienllamo.model.controllers;

import com.aquienllamo.aquienllamo.model.dtos.Request.PresupuestoDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.PresupuestoDTOResponse;
import com.aquienllamo.aquienllamo.model.services.PresupuestoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/presupuestos")
public class PresupuestoController {
    private final PresupuestoService presupuestoService;

    // crear presupuesto
    @PostMapping("/crear")
    public ResponseEntity<PresupuestoDTOResponse> crearPresupuesto(
            @Valid @RequestBody PresupuestoDTORequest dto,
            Authentication authentication) {

        // El 'name' del authentication sería el uuid
        String uuidTecnico = authentication.getName();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(presupuestoService.createPresupuesto(dto, uuidTecnico));
    }

    // ver presupuesto en detalle
    @GetMapping("/{uuid}")
    public ResponseEntity<PresupuestoDTOResponse> verPresupuesto(@PathVariable String uuid) {
        return ResponseEntity.ok(presupuestoService.obtenerPorUuid(uuid));
    }

    // aceptar presupuesto
    @PatchMapping("/{uuid}/aceptar")
    public ResponseEntity<Void> aceptarPresupuesto(
            @PathVariable String uuid,
            @RequestHeader("X-Usuario-UUID") String uuidUser) {

        presupuestoService.aceptarPresupuesto(uuid, uuidUser);
        return ResponseEntity.noContent().build(); // no devolvemos nada
    }

    // rechazar presupuesto
    @PatchMapping("/{uuid}/rechazar")
    public ResponseEntity<Void> rechazarPresupuesto(
            @PathVariable String uuid,
            @RequestHeader("X-Usuario-UUID") String uuidUser) {

        presupuestoService.rechazarPresupuesto(uuid, uuidUser);
        return ResponseEntity.noContent().build();
    }

    // cancelar presupuesto como técnico
    @PatchMapping("/{uuid}/cancelar")
    public ResponseEntity<Void> cancelarPresupuesto(
            @PathVariable String uuid,
            @RequestHeader("X-Tecnico-UUID") String uuidTecnico) {

        presupuestoService.cancelarPresupuesto(uuid, uuidTecnico);
        return ResponseEntity.noContent().build();
    }

    // ver todos los presupuestos -sirve p los admins, creo-
    @GetMapping("/todos")
    public ResponseEntity<List<PresupuestoDTOResponse>> listarTodos() {
        return ResponseEntity.ok(presupuestoService.getAllPresupuestos());
    }

    // búsqueda con filtros -> specs!!!!!
    @GetMapping("/buscar")
    public ResponseEntity<List<PresupuestoDTOResponse>> buscar(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String apellido,
            @RequestParam(required = false) BigDecimal min,
            @RequestParam(required = false) BigDecimal max,
            @RequestParam(required = false) LocalDate fecha) {

        return ResponseEntity.ok(presupuestoService.buscarPresupuestosCompleto(nombre, apellido, min, max, fecha));
    }

}
