package com.aquienllamo.aquienllamo.entidades.mensaje.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class MensajeDTORequest {
    @NotNull(message = "El id del chat no puede estar vacio")
    private String uuidChat;

    private String mensaje;

    private MultipartFile archivo;

    private String uuidMensaje;
}