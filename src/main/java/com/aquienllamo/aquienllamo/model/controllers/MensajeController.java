package com.aquienllamo.aquienllamo.model.controllers;

import com.aquienllamo.aquienllamo.model.dtos.Request.MensajeDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.MensajeDTOResponse;
import com.aquienllamo.aquienllamo.model.services.MensajeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/aquienllamo/mensajes")
@RequiredArgsConstructor
public class MensajeController {

    private final MensajeService mensajeService;

   //crear:
    @PostMapping(value = "/crear", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MensajeDTOResponse> crearMensaje(
            @RequestParam("uuidChat") String uuidChat,
            @RequestParam("uuidSender") String uuidSender,
            @RequestParam(value = "mensaje", required = false) String mensaje,
            @RequestParam(value = "archivo", required = false) MultipartFile archivo) {

        MensajeDTORequest dto = MensajeDTORequest.builder()
                .uuidChat(uuidChat)
                .uuidSender(uuidSender)
                .mensaje(mensaje)
                .archivo(archivo)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(mensajeService.crearMensaje(dto));
    }

    //modificar:
    @PatchMapping(value = "/modificar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MensajeDTOResponse> modificarMensaje(
            @RequestParam("uuidMensaje") String uuidMensaje,
            @RequestParam(value = "mensaje", required = false) String mensaje) {

        MensajeDTORequest dto = new MensajeDTORequest();
        dto.setUuidMensaje(uuidMensaje);
        dto.setMensaje(mensaje);
        return ResponseEntity.ok().body(mensajeService.modificarMensaje(dto));
    }

    //listar:
    @GetMapping("/listar")
    public ResponseEntity<List<MensajeDTOResponse>> listarMensajes(){
        return ResponseEntity.ok().body(mensajeService.listarMensajes());
    }
}
