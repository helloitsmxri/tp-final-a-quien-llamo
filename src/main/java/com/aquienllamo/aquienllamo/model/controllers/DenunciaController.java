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

    @GetMapping
    public ResponseEntity<List<DenunciaDTOResponse>> listarDenuncias(){
        return ResponseEntity.ok(denunciaService.listarDenuncias());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<DenunciaDTOResponse> buscarPorUuid(@PathVariable String uuid){
        return ResponseEntity.ok(denunciaService.buscarPorUuid(uuid));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<DenunciaDTOResponse>> listarPorEstado(@PathVariable EstadoDenunciaE estado){
        return ResponseEntity.ok(denunciaService.listarPorEstado(estado));
    }

    @PutMapping(value="/actualizar/{uuid}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DenunciaDTOResponse> actualizarDenuncia(@PathVariable String uuid, @Valid @ModelAttribute DenunciaDTORequest denuncia) {
        return ResponseEntity.ok(denunciaService.actualizarDenuncia(uuid, denuncia));
    }

    @PatchMapping("/cambiar-estado/{uuid}")
    public ResponseEntity<DenunciaDTOResponse> cambiarEstado(@PathVariable String uuid, @RequestParam EstadoDenunciaE estado) {
        return ResponseEntity.ok(denunciaService.cambiarEstado(uuid, estado));
    }

    @DeleteMapping("/eliminar/{uuid}")
    public ResponseEntity<Void> eliminarDenuncia(@PathVariable String uuid) {
        denunciaService.eliminarDenuncia(uuid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
