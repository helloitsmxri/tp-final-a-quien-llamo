package com.aquienllamo.aquienllamo.model.controllers;

import com.aquienllamo.aquienllamo.model.dtos.Request.CertificacionDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.CertificacionDTOResponse;
import com.aquienllamo.aquienllamo.model.services.CertificacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/aquienllamo/certificaciones")
public class CertificacionController {
    private final CertificacionService certificacionService;

    @GetMapping
    public ResponseEntity<List<CertificacionDTOResponse>> listarCertificaciones() {
        return ResponseEntity.ok(certificacionService.listarCertificaciones());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<CertificacionDTOResponse> buscarPorUuid(@PathVariable String uuid){
        return ResponseEntity.ok(certificacionService.buscarPorUuid(uuid));
    }

    @GetMapping("/tecnico/{uuidTecnico}")
    public ResponseEntity<List<CertificacionDTOResponse>> obtenerCertificacionesPorTecnico(@PathVariable String uuidTecnico){
        return ResponseEntity.ok(certificacionService.obtenerTecnicos(uuidTecnico));
    }

    @PostMapping("/tecnico/{uuidTecnico}")
    public ResponseEntity<CertificacionDTOResponse> crearCertificacion(@PathVariable String uuidTecnico, @Valid @RequestBody CertificacionDTORequest certificacionDTORequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(certificacionService.crearCertificacion(uuidTecnico, certificacionDTORequest));
    }

    @PutMapping("/actualizar/{uuid}")
    public ResponseEntity<CertificacionDTOResponse> actualizarCertificacion(@PathVariable String uuid, @Valid @RequestBody CertificacionDTORequest dto){
        return ResponseEntity.ok(certificacionService.actualizarCertificacion(uuid, dto));
    }

    @DeleteMapping("/eliminar/{uuid}")
    public ResponseEntity<Void> eliminarCertificacion(@PathVariable String uuid){
        certificacionService.eliminarCertificacion(uuid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
