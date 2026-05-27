package com.aquienllamo.aquienllamo.model.mappers;

import com.aquienllamo.aquienllamo.model.dtos.Request.MensajeDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.MensajeDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.ChatEntity;
import com.aquienllamo.aquienllamo.model.entities.MensajeEntity;
import org.springframework.stereotype.Component;

@Component
public class MensajeMapper {

    public MensajeDTOResponse toResponse(MensajeEntity mensaje)
    {
        if( mensaje == null )
        {
            return null;
        }
        return MensajeDTOResponse.builder()
                .mensaje(mensaje.getMensaje())
                .fechaMensaje(mensaje.getFechaMensaje())
                .sender(mensaje.getSender())
                .build();
    }

    public MensajeEntity toEntity(MensajeDTORequest dto, ChatEntity chat)
    {
        if(dto == null )
        {
            return null;
        }
        return MensajeEntity.builder()
                .idChat(chat)
                .mensaje(dto.getMensaje())
                .build();
    }
}
