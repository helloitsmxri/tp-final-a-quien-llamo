package com.aquienllamo.aquienllamo.model.controllers;

import com.aquienllamo.aquienllamo.model.Enum.EstadoVerificacion;
import com.aquienllamo.aquienllamo.model.dtos.Response.portfolio.PortfolioAdminDTOResponse;
import com.aquienllamo.aquienllamo.model.services.PortfolioAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/aquienllamo/portafolios/admin")
public class PortfolioAdminController {

    private final PortfolioAdminService service;

    //buscar un portfolio por uuid
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/{uuid}")
    public ResponseEntity<PortfolioAdminDTOResponse> buscarPortfolioPorUuid(@PathVariable String uuid){
        return ResponseEntity.ok(service.buscarPorfolioPorUuid(uuid));
    }

    //ver todos los portfolios
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping
    public ResponseEntity<List<PortfolioAdminDTOResponse>> listarPortfolios(){
        return ResponseEntity.ok().body(service.listarPortfolios());
    }

    //ver todos los portfolios con un estado en particular
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/estadoVerificacion")
    public ResponseEntity<List<PortfolioAdminDTOResponse>> listarPortfoliosPorEstado(@RequestParam EstadoVerificacion estadoVerificacion){
        return ResponseEntity.ok().body(service.listarPorEstado(estadoVerificacion));
    }

    //ver todos los portfolios dentro de un rango de tiempo
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/fecha")
    public ResponseEntity<List<PortfolioAdminDTOResponse>> listarPortfoliosPorRangoDeTiempo(
            @RequestParam LocalDateTime desde,
            @RequestParam LocalDateTime hasta){
        return ResponseEntity.ok().body(service.listarPorFecha(desde,hasta));
    }

    //ver todos los portfolios de una especialidad en particular
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/especialidad/{nombreEspecialidad}")
    public ResponseEntity<List<PortfolioAdminDTOResponse>> listarPortfoliosPorEspecialidad(@PathVariable String nombreEspecialidad){
        return ResponseEntity.ok().body(service.listarPorEspecialidad(nombreEspecialidad));
    }

    //ver portfolios de un tecnico
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/tecnico/{uuidTecnico}")
    public ResponseEntity<List<PortfolioAdminDTOResponse>> listarPortfoliosDeTecnico(@PathVariable String uuidTecnico){
        return ResponseEntity.ok().body(service.obtenerTecnicoPorUuid(uuidTecnico));
    }

    //aprobar portfolio
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PatchMapping("/aprobar/{uuid}")
    public ResponseEntity<PortfolioAdminDTOResponse> aprobarPorfolio(@PathVariable String uuid, @RequestParam(required = false) String notasAdmin){
        return ResponseEntity.ok(service.aprobarPortfolio(uuid, notasAdmin));
    }

    //rechazar portfolio
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PatchMapping("/rechazar/{uuid}")
    public ResponseEntity<PortfolioAdminDTOResponse> rechazarPorfolio(@PathVariable String uuid, @RequestParam String notasAdmin){
        return ResponseEntity.ok(service.rechazarPortfolio(uuid, notasAdmin));
    }
}
