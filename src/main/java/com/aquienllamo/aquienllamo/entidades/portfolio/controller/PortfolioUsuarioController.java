package com.aquienllamo.aquienllamo.entidades.portfolio.controller;

import com.aquienllamo.aquienllamo.entidades.portfolio.dto.request.PortfolioUsuarioDTORequest;
import com.aquienllamo.aquienllamo.entidades.portfolio.dto.response.PortfolioUsuarioDTOResponse;
import com.aquienllamo.aquienllamo.entidades.portfolio.service.PortfolioUsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/aquienllamo/portafolios/usuario")
public class PortfolioUsuarioController {

    private final PortfolioUsuarioService service;

    @PostMapping("/{uuidTecnico}")
    public ResponseEntity<PortfolioUsuarioDTOResponse> crearPortfolio (@Valid @RequestBody PortfolioUsuarioDTORequest request, @PathVariable String uuidTecnico){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearPortafolio(uuidTecnico, request));
    }

    @GetMapping("/{uuidPortfolio}")
    public ResponseEntity<PortfolioUsuarioDTOResponse> verPortfolio (@PathVariable String uuidPortfolio){
        return ResponseEntity.ok().body(service.verPortfolio(uuidPortfolio));
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','TECNICO')")
    @GetMapping("/tecnico/{uuidTecnico}")
    public ResponseEntity<List<PortfolioUsuarioDTOResponse>> listarPortfoliosEnviados(@PathVariable String uuidTecnico){
        return ResponseEntity.ok().body(service.verListadoPortfolios(uuidTecnico));
    }



}