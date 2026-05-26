package com.aquienllamo.aquienllamo.model.dtos.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ChatDTORequest {
    @NotBlank(message = "El id del usuario no puede estar vacio")
    private Integer idUsuario;
    @NotBlank(message = "El id del tecnico no puede estar vacio")
    private Integer idTecnico;

}
