package com.aquienllamo.aquienllamo.model.controllers;

import com.aquienllamo.aquienllamo.model.dtos.Request.UbicacionDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.UbicacionDTOResponse;
import com.aquienllamo.aquienllamo.model.services.UbicacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aquienllamo/ubicaciones")
@RequiredArgsConstructor
public class UbicacionController {

    private final UbicacionService ubicacionService;

    // crear ubicacion
    @PostMapping("/crear/{uuidUsuario}")
    public ResponseEntity<UbicacionDTOResponse> crearUbicacion(
            @PathVariable String uuidUsuario,
            @Valid @RequestBody UbicacionDTORequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ubicacionService.crearUbicacion(dto, uuidUsuario));
    }

    // obtener todas las ubicaciones de un usuario
    @GetMapping("/usuario/{uuidUsuario}")
    public ResponseEntity<List<UbicacionDTOResponse>> getUbicacionesByUsuario(
            @PathVariable String uuidUsuario) {
        return ResponseEntity.ok(ubicacionService.getUbicacionesByUsuario(uuidUsuario));
    }

    // obtener ubicacion por uuid
    @GetMapping("/{uuid}")
    public ResponseEntity<UbicacionDTOResponse> getUbicacionByUuid(
            @PathVariable String uuid) {
        return ResponseEntity.ok(ubicacionService.getUbicacionByUuid(uuid));
    }

    // actualizar ubicacion
    @PutMapping("/actualizar/{uuid}")
    public ResponseEntity<UbicacionDTOResponse> updateUbicacion(
            @PathVariable String uuid,
            @Valid @RequestBody UbicacionDTORequest dto) {
        return ResponseEntity.ok(ubicacionService.updateUbicacion(uuid, dto));
    }

    // eliminar ubicacion
    @DeleteMapping("/eliminar/{uuid}")
    public ResponseEntity<Void> deleteUbicacion(@PathVariable String uuid) {
        ubicacionService.deleteUbicacion(uuid);
        return ResponseEntity.noContent().build();
    }
}