package com.aquienllamo.aquienllamo.entidades.chat.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ChatDTORequest {
    // El usuario (cliente) se saca del token; solo hace falta saber con qué técnico se quiere hablar
    @NotBlank(message = "El uuid del técnico no puede estar vacío")
    private String uuidTecnico;
}