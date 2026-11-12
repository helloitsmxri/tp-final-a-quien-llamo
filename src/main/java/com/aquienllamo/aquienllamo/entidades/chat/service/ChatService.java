package com.aquienllamo.aquienllamo.entidades.chat.service;

import com.aquienllamo.aquienllamo.entidades.chat.dto.request.ChatDTORequest;
import com.aquienllamo.aquienllamo.entidades.chat.dto.response.ChatDTOResponse;
import com.aquienllamo.aquienllamo.entidades.chat.entity.ChatEntity;
import com.aquienllamo.aquienllamo.entidades.chat.exceptions.ChatNotFoundEx;
import com.aquienllamo.aquienllamo.entidades.chat.mapper.ChatMapper;
import com.aquienllamo.aquienllamo.entidades.chat.repository.ChatRepository;
import com.aquienllamo.aquienllamo.entidades.tecnico.entity.TecnicoEntity;
import com.aquienllamo.aquienllamo.entidades.tecnico.exceptions.TecnicoNotFoundEx;
import com.aquienllamo.aquienllamo.entidades.tecnico.repository.TecnicoRepository;
import com.aquienllamo.aquienllamo.entidades.usuario.entities.UsuarioEntity;
import com.aquienllamo.aquienllamo.entidades.usuario.exceptions.UserNotFoundEx;
import com.aquienllamo.aquienllamo.entidades.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatService {

    public final ChatMapper chatMapper;
    private final ChatRepository chatRepository;
    private final UsuarioRepository usuarioRepository;
    private final TecnicoRepository tecnicoRepository;

    // Inicia un chat entre el usuario logueado (cliente) y un técnico.
    // Si ya existe, devuelve el existente.
    public ChatDTOResponse iniciarChat(ChatDTORequest dto, String emailLogueado) {
        UsuarioEntity usuario = usuarioRepository.findByEmail(emailLogueado)
                .orElseThrow(() -> new UserNotFoundEx("No se encontró el usuario"));

        TecnicoEntity tecnico = tecnicoRepository.findByUuid(dto.getUuidTecnico())
                .orElseThrow(() -> new TecnicoNotFoundEx("No se encontró el técnico"));

        if (tecnico.getUsuario().getUuid().equals(usuario.getUuid())) {
            throw new IllegalArgumentException("No podés iniciar un chat con vos mismo");
        }

        return chatRepository.findByUsuario_UuidAndTecnico_Uuid(usuario.getUuid(), tecnico.getUuid())
                .map(chatMapper::toResponse)
                .orElseGet(() -> chatMapper.toResponse(
                        chatRepository.save(chatMapper.toEntity(usuario, tecnico))));
    }

    // Todos mis chats (como cliente y como técnico)
    @Transactional(readOnly = true)
    public List<ChatDTOResponse> listarMisChats(String emailLogueado) {
        return chatRepository.findMisChats(emailLogueado)
                .stream()
                .map(chatMapper::toResponse)
                .toList();
    }

    public ChatDTOResponse buscarPorUuid(String uuidChat) {
        ChatEntity chat = chatRepository.findByUuidChat(uuidChat)
                .orElseThrow(() -> new ChatNotFoundEx("El chat con ese id no existe"));
        return chatMapper.toResponse(chat);
    }
}