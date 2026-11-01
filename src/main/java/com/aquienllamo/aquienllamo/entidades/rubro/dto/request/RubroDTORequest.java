package com.aquienllamo.aquienllamo.entidades.rubro.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class RubroDTORequest {

    @NotBlank(message = "Debe seleccionar el nombre del rubro")
    private String nombreRubro;

    @NotEmpty(message = "Debe seleccionar al menos una especialidad.")
    private List<String> uuidEspecialidad;
}
