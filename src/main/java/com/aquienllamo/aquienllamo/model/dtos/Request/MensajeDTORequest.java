package com.aquienllamo.aquienllamo.model.dtos.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MensajeDTORequest {
    @NotNull
    private Integer idChat; // no se lo paso como chatEntity porque el usuario no me pasaría el objeto completo, solo el id del chat.

    @NotBlank
    private String mensaje;
}
