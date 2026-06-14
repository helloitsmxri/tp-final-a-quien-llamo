package com.aquienllamo.aquienllamo.model.controllers;

import com.aquienllamo.aquienllamo.model.dtos.Request.ChatDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.ChatDTOResponse;
import com.aquienllamo.aquienllamo.model.services.ChatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/aquienllamo/chats")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @PostMapping
    public  ResponseEntity<ChatDTOResponse> iniciarChat(@RequestBody @Valid ChatDTORequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(chatService.iniciarChat(dto));
    }

    @GetMapping("/buscar-por-uuid/{uuid}")
    public ResponseEntity<ChatDTOResponse> buscar(@PathVariable String uuid){
        return ResponseEntity.ok(chatService.buscarPorUuid(uuid));
    }

    @GetMapping("/listar-chats-por-usuario/{uuid}")
    public ResponseEntity<List<ChatDTOResponse>> listarPorUsuario(@PathVariable String uuid){
        return ResponseEntity.ok(chatService.listarUsuarios(uuid));
    }

    @GetMapping("/listar-chats-por-tecnico/{uuid}")
    public ResponseEntity<List<ChatDTOResponse>> listarPorTecnico(@PathVariable String uuid){
        return ResponseEntity.ok(chatService.listarTecnicos(uuid));
    }


}
