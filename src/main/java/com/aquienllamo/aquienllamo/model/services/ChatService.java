package com.aquienllamo.aquienllamo.model.services;

import com.aquienllamo.aquienllamo.model.dtos.Request.ChatDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.ChatDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.ChatEntity;
import com.aquienllamo.aquienllamo.model.entities.TecnicoEntity;
import com.aquienllamo.aquienllamo.model.entities.UsuarioEntity;
import com.aquienllamo.aquienllamo.model.exceptions.ChatNotFoundEx;
import com.aquienllamo.aquienllamo.model.exceptions.TecnicoNotFoundEx;
import com.aquienllamo.aquienllamo.model.exceptions.UserFoundEx;
import com.aquienllamo.aquienllamo.model.exceptions.UserNotFoundEx;
import com.aquienllamo.aquienllamo.model.mappers.ChatMapper;
import com.aquienllamo.aquienllamo.model.repositories.ChatRepository;
import com.aquienllamo.aquienllamo.model.repositories.TecnicoRepository;
import com.aquienllamo.aquienllamo.model.repositories.UsuarioRepository;
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

                return chatMapper.toResponse(chatRepository.save(chat));
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
