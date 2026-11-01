package com.aquienllamo.aquienllamo.entidades.chat.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ChatDTORequest {
    @NotNull(message = "El id del usuario no puede estar vacio")
    private Integer idUsuario;
    @NotNull(message = "El id del tecnico no puede estar vacio")
    private Integer idTecnico;

}
