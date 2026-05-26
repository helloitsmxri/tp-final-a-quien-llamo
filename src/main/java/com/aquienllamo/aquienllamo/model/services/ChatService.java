package com.aquienllamo.aquienllamo.model.services;

import com.aquienllamo.aquienllamo.model.dtos.Response.ChatDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.ChatEntity;
import com.aquienllamo.aquienllamo.model.exceptions.ChatNotFoundEx;
import com.aquienllamo.aquienllamo.model.mappers.ChatMapper;
import com.aquienllamo.aquienllamo.model.repositories.ChatRepository;
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

    //buscar por id:
    public ChatDTOResponse buscarPorId(Long id){
        ChatEntity chat= chatRepository.findById(id)
                .orElseThrow(()-> new ChatNotFoundEx("El chat con ese id no existe"));

                return chatMapper.toResponse(chatRepository.save(chat));
    }

    //listar usuario:
    public List<ChatDTOResponse> listarUsuarios()
    {
        return chatRepository.findByUsuario()
                .stream()
                .map(chatMapper::toResponse)
                .toList();
    }

    //listar tecnicos:
    public List<ChatDTOResponse> listarTecnicos()
    {
        return chatRepository.findByTecnico()
                .stream()
                .map(chatMapper::toResponse)
                .toList();
    }
}
