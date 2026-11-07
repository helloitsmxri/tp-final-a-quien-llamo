package com.aquienllamo.aquienllamo.entidades.chat.controller;

import com.aquienllamo.aquienllamo.entidades.chat.dto.request.ChatDTORequest;
import com.aquienllamo.aquienllamo.entidades.chat.dto.response.ChatDTOResponse;
import com.aquienllamo.aquienllamo.entidades.chat.service.ChatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aquienllamo/chats")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @PostMapping("/iniciar-chat")
    public ResponseEntity<ChatDTOResponse> iniciarChat(@RequestBody @Valid ChatDTORequest dto,
                                                       Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(chatService.iniciarChat(dto, authentication.getName()));
    }

    @GetMapping("/mis-chats")
    public ResponseEntity<List<ChatDTOResponse>> misChats(Authentication authentication) {
        return ResponseEntity.ok(chatService.listarMisChats(authentication.getName()));
    }
}