package com.aquienllamo.aquienllamo.model.dtos.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChatDTORequest {
    @NotBlank
    private Integer idUsuario;
    @NotBlank
    private Integer idTecnico;

}
