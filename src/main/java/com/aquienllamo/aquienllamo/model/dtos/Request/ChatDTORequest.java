package com.aquienllamo.aquienllamo.model.dtos.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

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
