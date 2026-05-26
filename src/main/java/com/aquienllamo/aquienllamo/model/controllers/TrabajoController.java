package com.aquienllamo.aquienllamo.model.controllers;

import com.aquienllamo.aquienllamo.model.Enum.EstadoTrabajo;
import com.aquienllamo.aquienllamo.model.dtos.Request.TrabajoDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.TrabajoDTOResponse;
import com.aquienllamo.aquienllamo.model.services.TrabajoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/aquienllamo/trabajos")
public class TrabajoController {

    private final TrabajoService trabajoService;

    @PostMapping
    public ResponseEntity<TrabajoDTOResponse> crearTrabajo (@Valid @RequestBody TrabajoDTORequest trabajoDTORequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(trabajoService.crearTrabajo(trabajoDTORequest));
    }

    @GetMapping
    public ResponseEntity<List<TrabajoDTOResponse>> listarTrabajos(){
        return ResponseEntity.ok().body(trabajoService.listarTrabajos());
    }

    @GetMapping("/listarTrabajos/{estadoTrabajo}")
    public ResponseEntity<List<TrabajoDTOResponse>> listarTrabajosPorEstadoTrabajo(@PathVariable EstadoTrabajo estadoTrabajo){
        return ResponseEntity.ok().body(trabajoService.listarTrabajoPorEstadoTrabajo(estadoTrabajo));
    }

}
