package com.aquienllamo.aquienllamo.model.dtos.Request;

import jakarta.validation.constraints.NotBlank;
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

    @NotNull(message = "El uuid del sender no puede estar vacío")
    private String uuidSender;

    @NotBlank(message = "El mensaje no puede estar vacio")
    private String mensaje;

    private MultipartFile archivo;
}
