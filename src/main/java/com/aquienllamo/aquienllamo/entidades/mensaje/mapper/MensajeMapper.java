package com.aquienllamo.aquienllamo.entidades.mensaje.mapper;

import com.aquienllamo.aquienllamo.entidades.mensaje.dto.response.MensajeDTOResponse;
import com.aquienllamo.aquienllamo.entidades.mensaje.entity.MensajeEntity;
import org.springframework.stereotype.Component;

@Component
public class MensajeMapper {

    public MensajeDTOResponse toResponse(MensajeEntity mensaje) {
        if (mensaje == null) {
            return null;
        }
        return MensajeDTOResponse.builder()
                .uuidMensaje(mensaje.getUuidMensaje())
                .uuidChat(mensaje.getChat().getUuidChat())
                .uuidSender(mensaje.getSender().getUuid())
                .nombreSender(mensaje.getSender().getNombre())
                .mensaje(mensaje.getMensaje())
                .fechaMensaje(mensaje.getFechaMensaje())
                .archivoUrl(mensaje.getArchivoUrl())
                .tipoArchivo(mensaje.getTipoArchivo())
                .build();
    }
}