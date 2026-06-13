package com.aquienllamo.aquienllamo.model.dtos.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class MensajeDTORequest {
    @NotBlank(message = "El id del chat no puede estar vacio")
    private String uuidChat;

    @NotBlank(message = "El uuid del sender no puede estar vacío")
    private String uuidSender;

    @NotBlank(message = "El mensaje no puede estar vacio")
    private String mensaje;
}
