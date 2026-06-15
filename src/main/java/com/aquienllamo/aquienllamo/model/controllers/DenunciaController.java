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
import org.springframework.security.core.Authentication;
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

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping
    public ResponseEntity<List<DenunciaDTOResponse>> listarDenuncias(){
        return ResponseEntity.ok(denunciaService.listarDenuncias());
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/{uuid}")
    public ResponseEntity<DenunciaDTOResponse> buscarPorUuid(@PathVariable String uuid){
        return ResponseEntity.ok(denunciaService.buscarPorUuid(uuid));
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<DenunciaDTOResponse>> listarPorEstado(@PathVariable EstadoDenunciaE estado){
        return ResponseEntity.ok(denunciaService.listarPorEstado(estado));
    }

//    @PutMapping(value="/actualizar/{uuid}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<DenunciaDTOResponse> actualizarDenuncia(@PathVariable String uuid, @Valid @ModelAttribute DenunciaDTORequest denuncia) {
//        return ResponseEntity.ok(denunciaService.actualizarDenuncia(uuid, denuncia));
//    }


    @DeleteMapping("/eliminar/{uuid}")
    public ResponseEntity<Void> eliminarDenuncia(@PathVariable String uuid) {
        denunciaService.eliminarDenuncia(uuid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/sin-asignar")
    public ResponseEntity<List<DenunciaDTOResponse>> obtenerDenunciasSinAsignar(){
        return ResponseEntity.ok(denunciaService.obtenerDenunciasSinAsignar());
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PatchMapping("/{uuidDenuncia}/asignar-administrador/{uuidAdmin}")
    public ResponseEntity<DenunciaDTOResponse> asignarAdministrador(@PathVariable String uuidDenuncia, @PathVariable String uuidAdmin){
        return ResponseEntity.ok(denunciaService.asignarAdministrador(uuidDenuncia, uuidAdmin));
    }

    @PatchMapping("/{uuid}/aprobar")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public DenunciaDTOResponse aprobar(@PathVariable String uuid, @RequestParam String mensaje){
        return denunciaService.aprobarDenuncia(uuid, mensaje);
    }

    @PatchMapping("/{uuid}/rechazar")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public DenunciaDTOResponse rechazar(@PathVariable String uuid, @RequestParam String mensaje){
        return denunciaService.rechazarDenuncia(uuid, mensaje);
    }


    @GetMapping("/ordenar")
    public List<DenunciaDTOResponse> ordenar(@RequestParam(defaultValue = "desc") String orden) {

        if (orden.equalsIgnoreCase("asc")) {
            return denunciaService.denunciasMasViejasPrimero();
        }

        return denunciaService.denunciasMasNuevasPrimero();
    }

    @GetMapping("/mis-denuncias")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public List<DenunciaDTOResponse> verMisDenuncias(@RequestParam(required = false) EstadoDenunciaE estado){
        return denunciaService.misDenuncias(estado);
    }
}
