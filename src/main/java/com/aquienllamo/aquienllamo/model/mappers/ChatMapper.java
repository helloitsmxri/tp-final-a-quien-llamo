package com.aquienllamo.aquienllamo.model.mappers;

import com.aquienllamo.aquienllamo.model.dtos.Response.ChatDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.ChatEntity;
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

    public ChatEntity toEntity(ChatDTOResponse dto)
    {
        if(dto==null)
            {
            return null;
            }
        return ChatEntity.builder()
                .uuidChat(dto.getUuidChat())
                .fechaChat(dto.getFechaChat())
                .build();
    }
}
