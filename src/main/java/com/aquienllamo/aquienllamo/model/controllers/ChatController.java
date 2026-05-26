package com.aquienllamo.aquienllamo.model.controllers;

import com.aquienllamo.aquienllamo.model.dtos.Response.ChatDTOResponse;
import com.aquienllamo.aquienllamo.model.services.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/aquienllamo/chats")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @GetMapping("/buscar por id/{id}")
    public ResponseEntity<ChatDTOResponse> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(chatService.buscarPorId(id));
    }

    @GetMapping("/listar chats por usuario/{id}")
    public ResponseEntity<List<ChatDTOResponse>> listarPorUsuario(@PathVariable Integer id){
        return ResponseEntity.ok(chatService.listarUsuarios(id));
    }

    @GetMapping("/listar chats por tecnico/{id}")
    public ResponseEntity<List<ChatDTOResponse>> listarPorTecnico(@PathVariable Integer id){
        return ResponseEntity.ok(chatService.listarTecnicos(id));
    }

    @GetMapping("/buscar por uuid/{uuid}")
    public ResponseEntity<ChatDTOResponse> buscarPorUuid(@PathVariable String uuid){
        return ResponseEntity.ok(chatService.buscarPorUuid(uuid));
    }

}
