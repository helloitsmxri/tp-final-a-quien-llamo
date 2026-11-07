package com.aquienllamo.aquienllamo.entidades.mensaje.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class MensajeDTOResponse {

    private String uuidMensaje;
    private String uuidChat;
    private String uuidSender;
    private String nombreSender;
    private String mensaje;

    // Como texto ISO. Sin esto, por WebSocket llega como un array [2026,11,7,...]
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime fechaMensaje;

    private String archivoUrl;    // antes se llamaba "archivo"
    private String tipoArchivo;
}