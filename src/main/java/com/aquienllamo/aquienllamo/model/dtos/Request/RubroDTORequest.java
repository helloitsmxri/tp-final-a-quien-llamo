package com.aquienllamo.aquienllamo.model.dtos.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
