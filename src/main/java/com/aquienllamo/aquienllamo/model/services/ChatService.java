package com.aquienllamo.aquienllamo.model.services;

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

    //buscar por id:
    public ChatDTOResponse buscarPorId(Long id){
        ChatEntity chat= chatRepository.findById(id)
                .orElseThrow(()-> new ChatNotFoundEx("El chat con ese id no existe"));

                return chatMapper.toResponse(chatRepository.save(chat));
    }

    //listar chats por usuario:
    public List<ChatDTOResponse> listarUsuarios(Integer idUsuario)
    {
        UsuarioEntity usuario= usuarioRepository.findById(idUsuario)
                .orElseThrow(()-> new UserNotFoundEx("El usuario con ese id no se encuentra"));

        return chatRepository.findByUsuario(usuario.getIdUsuario())
                .stream()
                .map(chatMapper::toResponse)
                .toList();
    }

    //listar chats por tecnico:
    public List<ChatDTOResponse> listarTecnicos(Integer idTecnico)
    {
        TecnicoEntity tecnico= tecnicoRepository.findById(idTecnico)
                .orElseThrow(()-> new TecnicoNotFoundEx("El tecnico con ese id no se encuentra"));
        return chatRepository.findByTecnico(tecnico.getIdTecnico())
                .stream()
                .map(chatMapper::toResponse)
                .toList();
    }

    //buscar por uuid:
    public ChatDTOResponse buscarPorUuid(String uuid)
    {
        ChatEntity chat = chatRepository.findByUuidChat(uuid)
                .orElseThrow(()-> new ChatNotFoundEx("El chat con ese id no se encuentra"));

        return chatMapper.toResponse(chatRepository.save(chat));
    }
}
