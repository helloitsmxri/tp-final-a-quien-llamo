package com.aquienllamo.aquienllamo.model.controllers;

import com.aquienllamo.aquienllamo.model.dtos.Request.MensajeDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.MensajeDTOResponse;
import com.aquienllamo.aquienllamo.model.services.MensajeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aquienllamo/mensajes")
@RequiredArgsConstructor
public class MensajeController {

    private final MensajeService mensajeService;

    //crear:
    @PostMapping("/crear")
    public ResponseEntity<MensajeDTOResponse> crearMensaje(@Valid @RequestBody MensajeDTORequest dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(mensajeService.crearMensaje(dto));
    }

    //modificar:
    @PatchMapping("/modificar")
    public ResponseEntity<MensajeDTOResponse> modificarMensaje(@RequestBody MensajeDTORequest dto){
        return ResponseEntity.ok().body(mensajeService.modificarMensaje(dto));
    }

    //listar:
    @GetMapping("/listar")
    public ResponseEntity<List<MensajeDTOResponse>> listarMensajes(){
        return ResponseEntity.ok().body(mensajeService.listarMensajes());
    }
}
