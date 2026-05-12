package com.aquienllamo.aquienllamo.model.controllers;

import com.aquienllamo.aquienllamo.model.Enum.TipoValidacion;
import com.aquienllamo.aquienllamo.model.dtos.Request.EspecialidadDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.EspecialidadDTOResponse;
import com.aquienllamo.aquienllamo.model.services.EspecialidadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aquienllamo/especialidades")
@RequiredArgsConstructor
public class EspecialidadController {
    private final EspecialidadService especialidadService;

    //crear especialidad
    @PostMapping
    public ResponseEntity<EspecialidadDTOResponse> createEspecialidad(@Valid @RequestBody EspecialidadDTORequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(especialidadService.createEspecialidad(dto));
    }

    //listar todas las especialidades
    @GetMapping
    public ResponseEntity<List<EspecialidadDTOResponse>> getAllEspecialidades() {
        return ResponseEntity.ok(especialidadService.getAllEspecialidades());
    }

    //buscar especialidad por uuid
    @GetMapping("/{uuid}")
    public ResponseEntity<EspecialidadDTOResponse> getEspecialidadByUuid(@PathVariable String uuid) {
        return ResponseEntity.ok(especialidadService.getEspecialidadByUuid(uuid));
    }

    //buscar especialidad por nombre
    @GetMapping("/buscar")
    public ResponseEntity<List<EspecialidadDTOResponse>> getEspecialidadesByNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(especialidadService.getEspecialidadesByNombre(nombre));
    }

    //filtrar por tipo de validacion
    @GetMapping("/tipo-validacion")
    public ResponseEntity<List<EspecialidadDTOResponse>> getEspecialidadesByTipoValidacion(@RequestParam TipoValidacion tipoValidacion) {
        return ResponseEntity.ok(especialidadService.getEspecialidadesByTipoValidacion(tipoValidacion));
    }

    //actualizar especialidad
    @PutMapping("/actualizar/{uuid}")
    public ResponseEntity<EspecialidadDTOResponse> updateEspecialidad(@PathVariable String uuid, @Valid @RequestBody EspecialidadDTORequest dto) {
        return ResponseEntity.ok(especialidadService.updateEspecialidad(uuid, dto));
    }

    //eliminar especialidad
    @DeleteMapping("/eliminar/{uuid}")
    public ResponseEntity<Void> deleteEspecialidad(@PathVariable String uuid) {
        especialidadService.deleteEspecialidad(uuid);
        return ResponseEntity.noContent().build();
    }
}
