package com.aquienllamo.aquienllamo.model.mappers;

import com.aquienllamo.aquienllamo.model.dtos.Request.MensajeDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.MensajeDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.ChatEntity;
import com.aquienllamo.aquienllamo.model.entities.MensajeEntity;
import com.aquienllamo.aquienllamo.model.entities.UsuarioEntity;
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
                .nombreSender(mensaje.getSender().getNombre())
                .uuidMensaje(mensaje.getUuidMensaje())
                .archivo(mensaje.getArchivoUrl())
                .tipoArchivo(mensaje.getTipoArchivo())
                .build();
    }

    public MensajeEntity toEntity(MensajeDTORequest dto, ChatEntity chat, UsuarioEntity sender)
    {
        if(dto == null )
        {
            return null;
        }
        return MensajeEntity.builder()
                .chat(chat)
                .mensaje(dto.getMensaje())
                .sender(sender)
                .build();
    }
}
