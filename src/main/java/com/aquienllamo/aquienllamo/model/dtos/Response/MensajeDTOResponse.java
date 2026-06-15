package com.aquienllamo.aquienllamo.model.dtos.Response;

import com.aquienllamo.aquienllamo.model.entities.UsuarioEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class MensajeDTOResponse {

    private String mensaje;
    private LocalDateTime fechaMensaje;
    private String nombreSender;
    private String archivoUrl;
    private String tipoArchivo;
}
