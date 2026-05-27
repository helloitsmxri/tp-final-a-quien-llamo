package com.aquienllamo.aquienllamo.model.controllers;

import com.aquienllamo.aquienllamo.model.Enum.EstadoVerificacion;
import com.aquienllamo.aquienllamo.model.dtos.Request.PortfolioAdminDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.PortfolioAdminDTOResponse;
import com.aquienllamo.aquienllamo.model.services.PortfolioAdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/aquienllamo/portafolios/admin")
public class PortfolioAdminController {

    private final PortfolioAdminService service;

    //ver todos los portfolios
    @GetMapping
    public ResponseEntity<List<PortfolioAdminDTOResponse>> listarPortfolios(){
        return ResponseEntity.ok().body(service.listarPortfolios());
    }

    //ver todos los portfolios con un estado en particular
    @GetMapping("/estadoVerificacion")
    public ResponseEntity<List<PortfolioAdminDTOResponse>> listarPortfoliosPorEstado(@RequestParam EstadoVerificacion estadoVerificacion){
        return ResponseEntity.ok().body(service.listarPorEstado(estadoVerificacion));
    }

    //ver todos los portfolios dentro de un rango de tiempo
    @GetMapping("/fecha")
    public ResponseEntity<List<PortfolioAdminDTOResponse>> listarPortfoliosPorRangoDeTiempo(
            @RequestParam LocalDateTime desde,
            @RequestParam LocalDateTime hasta){
        return ResponseEntity.ok().body(service.listarPorFecha(desde,hasta));
    }

    //ver todos los portfolios de una especialidad en particular
    @GetMapping("/especialidad/{nombreEspecialidad}")
    public ResponseEntity<List<PortfolioAdminDTOResponse>> listarPortfoliosPorEspecialidad(@PathVariable String nombreEspecialidad){
        return ResponseEntity.ok().body(service.listarPorEspecialidad(nombreEspecialidad));
    }

    //ver portfolios de un tecnico
    @GetMapping("/tecnico/{uuidTecnico}")
    public ResponseEntity<List<PortfolioAdminDTOResponse>> listarPortfoliosDeTecnico(@PathVariable String uuidTecnico){
        return ResponseEntity.ok().body(service.obtenerTecnicoPorUuid(uuidTecnico));
    }

    //revision de portfolio
    @PatchMapping("/{uuid}")
    public ResponseEntity<PortfolioAdminDTOResponse> revisionPortfolio (@PathVariable String uuid, @Valid @RequestBody PortfolioAdminDTORequest request){
        return ResponseEntity.ok().body(service.revisionPortfolio(uuid, request));
    }


}
