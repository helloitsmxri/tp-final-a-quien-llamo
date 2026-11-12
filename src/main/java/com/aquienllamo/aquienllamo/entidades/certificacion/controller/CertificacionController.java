package com.aquienllamo.aquienllamo.entidades.certificacion.controller;

import com.aquienllamo.aquienllamo.administrador.enums.EstadoVerificacion;
import com.aquienllamo.aquienllamo.entidades.certificacion.dtos.request.CertificacionDTOActualizarRequest;
import com.aquienllamo.aquienllamo.entidades.certificacion.dtos.response.CertificacionPublicaDTOResponse;
import com.aquienllamo.aquienllamo.entidades.certificacion.service.CertificacionService;
import com.aquienllamo.aquienllamo.entidades.certificacion.dtos.request.CertificacionDTORequest;
import com.aquienllamo.aquienllamo.entidades.certificacion.dtos.response.CertificacionDTOResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/aquienllamo/certificaciones")
public class CertificacionController {
    private final CertificacionService certificacionService;

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping
    public ResponseEntity<List<CertificacionDTOResponse>> listarCertificaciones() {
        return ResponseEntity.ok(certificacionService.listarCertificaciones());
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/{uuid}")
    public ResponseEntity<CertificacionDTOResponse> buscarPorUuid(@PathVariable String uuid){
        return ResponseEntity.ok(certificacionService.buscarPorUuid(uuid));
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','TECNICO')")
    @GetMapping("/tecnico/{uuidTecnico}")
    public ResponseEntity<List<CertificacionDTOResponse>> obtenerCertificacionesPorTecnico(@PathVariable String uuidTecnico){
        return ResponseEntity.ok(certificacionService.obtenerTecnicos(uuidTecnico));
    }

    @PreAuthorize("hasRole('TECNICO')")
    @PostMapping(value="/tecnico/{uuidTecnico}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CertificacionDTOResponse> crearCertificacion(@PathVariable String uuidTecnico, @Valid @ModelAttribute CertificacionDTORequest certificacionDTORequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(certificacionService.crearCertificacion(uuidTecnico, certificacionDTORequest));
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'TECNICO')")
    @PutMapping(value="/actualizar/{uuid}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CertificacionDTOResponse> actualizarCertificacion(
            @PathVariable String uuid,
            @Valid @ModelAttribute CertificacionDTOActualizarRequest dto) {

        return ResponseEntity.ok(certificacionService.actualizarCertificacion(uuid, dto));
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/eliminar/{uuid}")
    public ResponseEntity<Void> eliminarCertificacion(@PathVariable String uuid){
        certificacionService.eliminarCertificacion(uuid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<CertificacionDTOResponse>> filtrarPorEstado(@PathVariable EstadoVerificacion estado){
        return ResponseEntity.ok(certificacionService.filtrarPorEstado(estado));
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PatchMapping("/aprobar/{uuid}")
    public ResponseEntity<CertificacionDTOResponse> aprobarCertificacion(@PathVariable String uuid){
        return ResponseEntity.ok(certificacionService.aprobarCertificacion(uuid));
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PatchMapping("/rechazar/{uuid}")
    public ResponseEntity<CertificacionDTOResponse> rechazarCertificacion(@PathVariable String uuid, @RequestParam String motivo) {
        return ResponseEntity.ok(certificacionService.rechazarCertificacion(uuid, motivo));
    }

    @GetMapping("/publico/tecnico/{uuidTecnico}")
    public ResponseEntity<List<CertificacionPublicaDTOResponse>> certificacionesPublicas(@PathVariable String uuidTecnico){
        return ResponseEntity.ok(certificacionService.obtenerPublicas(uuidTecnico));
    }
}
