package com.aquienllamo.aquienllamo.model.mappers;

import com.aquienllamo.aquienllamo.model.dtos.Request.ChatDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.ChatDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.ChatEntity;
import com.aquienllamo.aquienllamo.model.entities.TecnicoEntity;
import com.aquienllamo.aquienllamo.model.entities.UsuarioEntity;
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
