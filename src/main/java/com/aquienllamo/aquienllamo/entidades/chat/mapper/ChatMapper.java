package com.aquienllamo.aquienllamo.entidades.chat.mapper;

import com.aquienllamo.aquienllamo.entidades.chat.dto.response.ChatDTOResponse;
import com.aquienllamo.aquienllamo.entidades.chat.entity.ChatEntity;
import com.aquienllamo.aquienllamo.entidades.tecnico.entity.TecnicoEntity;
import com.aquienllamo.aquienllamo.entidades.usuario.entities.UsuarioEntity;
import org.springframework.stereotype.Component;

@Component
public class ChatMapper {

    public ChatDTOResponse toResponse(ChatEntity chat) {
        if (chat == null) {
            return null;
        }
        UsuarioEntity usuarioTecnico = chat.getTecnico().getUsuario();

        return ChatDTOResponse.builder()
                .uuidChat(chat.getUuidChat())
                .fechaChat(chat.getFechaChat())
                .uuidUsuario(chat.getUsuario().getUuid())
                .nombreUsuario(nombreCompleto(chat.getUsuario()))
                .uuidTecnico(chat.getTecnico().getUuid())
                .nombreTecnico(nombreCompleto(usuarioTecnico))
                .build();
    }

    public ChatEntity toEntity(UsuarioEntity usuario, TecnicoEntity tecnico) {
        return ChatEntity.builder()
                .usuario(usuario)
                .tecnico(tecnico)
                .build();
    }

    private String nombreCompleto(UsuarioEntity u) {
        return (u.getNombre() + " " + u.getApellido()).trim();
    }
}