package com.aquienllamo.aquienllamo.entidades.chat.mapper;

import com.aquienllamo.aquienllamo.entidades.chat.dto.request.ChatDTORequest;
import com.aquienllamo.aquienllamo.entidades.chat.dto.response.ChatDTOResponse;
import com.aquienllamo.aquienllamo.entidades.chat.entity.ChatEntity;
import com.aquienllamo.aquienllamo.entidades.tecnico.entity.TecnicoEntity;
import com.aquienllamo.aquienllamo.entidades.usuario.entities.UsuarioEntity;
import org.springframework.stereotype.Component;

@Component
public class ChatMapper {

    public ChatDTOResponse toResponse(ChatEntity chat)
    {
        if(chat==null)
        {
            return null;
        }
        return ChatDTOResponse.builder()
                .uuidChat(chat.getUuidChat())
                .fechaChat(chat.getFechaChat())
                .build();
    }

    public ChatEntity toEntity(ChatDTORequest dto, UsuarioEntity usuario, TecnicoEntity tecnico)
    {
        if(dto==null)
            {
            return null;
            }
        return ChatEntity.builder()
                .usuario(usuario)
                .tecnico(tecnico)
                .build();
    }
}
