package com.aquienllamo.aquienllamo.model.dtos.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HabilidadDTORequest {
    @NotBlank(message = "El nombre de la habilidad es obligatorio.")
    @Size(max = 50, message = "El nombre de la habilidad no puede superar los 50 caracteres.")
    private String nombreHabilidad;
}
