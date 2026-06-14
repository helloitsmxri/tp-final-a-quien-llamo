package com.aquienllamo.aquienllamo.model.controllers;

import com.aquienllamo.aquienllamo.model.Enum.EstadoDenunciaE;
import com.aquienllamo.aquienllamo.model.dtos.Request.DenunciaDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.DenunciaDTOResponse;
import com.aquienllamo.aquienllamo.model.services.DenunciaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/aquienllamo/denuncias")
@RestController
@RequiredArgsConstructor
public class DenunciaController {
    private final DenunciaService denunciaService;

    @PostMapping(value="/crear/{uuidChat}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DenunciaDTOResponse> crearDenuncia(@PathVariable String uuidChat, @Valid @ModelAttribute DenunciaDTORequest denuncia) {
        return ResponseEntity.status(HttpStatus.CREATED).body(denunciaService.crearDenuncia(denuncia, uuidChat));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<DenunciaDTOResponse>> listarDenuncias(){
        return ResponseEntity.ok(denunciaService.listarDenuncias());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{uuid}")
    public ResponseEntity<DenunciaDTOResponse> buscarPorUuid(@PathVariable String uuid){
        return ResponseEntity.ok(denunciaService.buscarPorUuid(uuid));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<DenunciaDTOResponse>> listarPorEstado(@PathVariable EstadoDenunciaE estado){
        return ResponseEntity.ok(denunciaService.listarPorEstado(estado));
    }

    @PutMapping(value="/actualizar/{uuid}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DenunciaDTOResponse> actualizarDenuncia(@PathVariable String uuid, @Valid @ModelAttribute DenunciaDTORequest denuncia) {
        return ResponseEntity.ok(denunciaService.actualizarDenuncia(uuid, denuncia));
    }


    @DeleteMapping("/eliminar/{uuid}")
    public ResponseEntity<Void> eliminarDenuncia(@PathVariable String uuid) {
        denunciaService.eliminarDenuncia(uuid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/sin-asignar")
    public ResponseEntity<List<DenunciaDTOResponse>> obtenerDenunciasSinAsignar(){
        return ResponseEntity.ok(denunciaService.obtenerDenunciasSinAsignar());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{uuidDenuncia}/asignar-administrador/{uuidAdmin}")
    public ResponseEntity<DenunciaDTOResponse> asignarAdministrador(@PathVariable String uuidDenuncia, @PathVariable String uuidAdmin){
        return ResponseEntity.ok(denunciaService.asignarAdministrador(uuidDenuncia, uuidAdmin));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/aprobar/{uuid}")
    public ResponseEntity<DenunciaDTOResponse> aprobarDenuncia(@PathVariable String uuid){
        return ResponseEntity.ok(denunciaService.aprobarDenuncia(uuid));
    }

}
