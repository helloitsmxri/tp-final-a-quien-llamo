package com.aquienllamo.aquienllamo.entidades.chat.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ChatDTOResponse {
    private String uuidChat;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime fechaChat;

    // El cliente del chat
    private String uuidUsuario;
    private String nombreUsuario;

    // El técnico del chat
    private String uuidTecnico;
    private String nombreTecnico;
}