package com.aquienllamo.aquienllamo.entidades.mensaje.controller;

import com.aquienllamo.aquienllamo.entidades.mensaje.dto.request.MensajeDTORequest;
import com.aquienllamo.aquienllamo.entidades.mensaje.dto.response.MensajeDTOResponse;
import com.aquienllamo.aquienllamo.entidades.mensaje.services.MensajeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/aquienllamo/mensajes")
@RequiredArgsConstructor
public class MensajeController {

    private final MensajeService mensajeService;
    private final SimpMessagingTemplate messagingTemplate;

    // crear: guarda el mensaje y lo publica en vivo
    @PostMapping(value = "/crear", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MensajeDTOResponse> crearMensaje(
            @RequestParam("uuidChat") String uuidChat,
            @RequestParam(value = "mensaje", required = false) String mensaje,
            @RequestParam(value = "archivo", required = false) MultipartFile archivo,
            Authentication authentication) {

        MensajeDTORequest dto = MensajeDTORequest.builder()
                .uuidChat(uuidChat)
                .mensaje(mensaje)
                .archivo(archivo)
                .build();

        // El service termina su transacción al volver: el mensaje ya está guardado en la base
        MensajeDTOResponse creado = mensajeService.crearMensaje(dto, authentication.getName());

        // Avisar a todos los que tienen este chat abierto
        messagingTemplate.convertAndSend("/topic/chat/" + uuidChat, creado);

        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // mensajes de un chat (solo si participo)
    @GetMapping("/chat/{uuidChat}")
    public ResponseEntity<List<MensajeDTOResponse>> listarPorChat(@PathVariable String uuidChat,
                                                                  Authentication authentication) {
        return ResponseEntity.ok(mensajeService.listarPorChat(uuidChat, authentication.getName()));
    }

    // modificar:
    @PatchMapping(value = "/modificar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MensajeDTOResponse> modificarMensaje(
            @RequestParam("uuidMensaje") String uuidMensaje,
            @RequestParam(value = "mensaje", required = false) String mensaje) {

        MensajeDTORequest dto = new MensajeDTORequest();
        dto.setUuidMensaje(uuidMensaje);
        dto.setMensaje(mensaje);
        return ResponseEntity.ok().body(mensajeService.modificarMensaje(dto));
    }

    // listar TODOS: solo administradores
    @GetMapping("/listar")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<MensajeDTOResponse>> listarMensajes() {
        return ResponseEntity.ok().body(mensajeService.listarMensajes());
    }
}