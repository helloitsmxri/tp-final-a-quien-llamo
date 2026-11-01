package com.aquienllamo.aquienllamo.entidades.mensaje.mapper;

import com.aquienllamo.aquienllamo.entidades.chat.entity.ChatEntity;
import com.aquienllamo.aquienllamo.entidades.mensaje.dto.request.MensajeDTORequest;
import com.aquienllamo.aquienllamo.entidades.mensaje.dto.response.MensajeDTOResponse;
import com.aquienllamo.aquienllamo.entidades.mensaje.entity.MensajeEntity;
import com.aquienllamo.aquienllamo.entidades.usuario.entities.UsuarioEntity;
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
