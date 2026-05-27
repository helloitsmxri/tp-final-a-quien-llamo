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
    @NotNull(message = "El id del chat no puede estar vacio")
    private String uuidChat;

    @NotBlank(message = "El mensaje no puede estar vacio")
    private String mensaje;
}
