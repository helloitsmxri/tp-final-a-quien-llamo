package com.aquienllamo.aquienllamo.entidades.chat.service;

import com.aquienllamo.aquienllamo.entidades.chat.dto.request.ChatDTORequest;
import com.aquienllamo.aquienllamo.entidades.chat.dto.response.ChatDTOResponse;
import com.aquienllamo.aquienllamo.entidades.chat.mapper.ChatMapper;
import com.aquienllamo.aquienllamo.entidades.chat.exceptions.ChatNotFoundEx;
import com.aquienllamo.aquienllamo.entidades.chat.entity.ChatEntity;
import com.aquienllamo.aquienllamo.entidades.chat.repository.ChatRepository;
import com.aquienllamo.aquienllamo.entidades.tecnico.entity.TecnicoEntity;
import com.aquienllamo.aquienllamo.entidades.usuario.entities.UsuarioEntity;
import com.aquienllamo.aquienllamo.entidades.tecnico.exceptions.TecnicoNotFoundEx;
import com.aquienllamo.aquienllamo.entidades.usuario.exceptions.UserNotFoundEx;
import com.aquienllamo.aquienllamo.entidades.tecnico.repository.TecnicoRepository;
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

    //iniciar chat:
    public ChatDTOResponse iniciarChat(ChatDTORequest dto) {
        UsuarioEntity usuario = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> new UserNotFoundEx("No se encontró el usuario"));

        TecnicoEntity tecnico = tecnicoRepository.findById(dto.getIdTecnico())
                .orElseThrow(() -> new TecnicoNotFoundEx("No se encontró el técnico"));

      //evitar chats duplicados:
        if (chatRepository.existsByUsuario_IdUsuarioAndTecnico_IdTecnico(
                usuario.getIdUsuario(), tecnico.getIdTecnico())) {
            // si ya existe, devolver el chat existente
            return chatRepository.findByUsuario_Uuid(usuario.getUuid())
                    .stream()
                    .filter(c -> c.getTecnico().getIdTecnico().equals(tecnico.getIdTecnico()))
                    .findFirst()
                    .map(chatMapper::toResponse)
                    .orElseThrow(() -> new ChatNotFoundEx("No se encontró el chat"));
        }

        ChatEntity chat = chatMapper.toEntity(dto, usuario, tecnico);
        return chatMapper.toResponse(chatRepository.save(chat));
    }


    //buscar por id:
    public ChatDTOResponse buscarPorUuid(String uuidChat){
        ChatEntity chat= chatRepository.findByUuidChat(uuidChat)
                .orElseThrow(()-> new ChatNotFoundEx("El chat con ese id no existe"));

                return chatMapper.toResponse(chat);
    }

    //listar chats por usuario:
    public List<ChatDTOResponse> listarUsuarios(String uuidUsuario)
    {
        UsuarioEntity usuario= usuarioRepository.findByUuid(uuidUsuario)
                .orElseThrow(()-> new UserNotFoundEx("El usuario con ese id no se encuentra"));

        return chatRepository.findByUsuario_Uuid(usuario.getUuid())
                .stream()
                .map(chatMapper::toResponse)
                .toList();
    }

    //listar chats por tecnico:
    public List<ChatDTOResponse> listarTecnicos(String uuidTecnico)
    {
        TecnicoEntity tecnico= tecnicoRepository.findByUuid(uuidTecnico)
                .orElseThrow(()-> new TecnicoNotFoundEx("El tecnico con ese id no se encuentra"));
        return chatRepository.findByTecnico_Uuid(tecnico.getUuid())
                .stream()
                .map(chatMapper::toResponse)
                .toList();
    }
}
