package com.aquienllamo.aquienllamo.model.dtos.Request;

import com.aquienllamo.aquienllamo.model.Enum.TipoValidacion;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EspecialidadDTORequest {

    @NotBlank(message = "El nombre de la especialidad es obligatorio.")
    @Size(max = 50, message = "El nombre de la especialidad no puede superar los 50 caracteres.")
    private String nombreEspecialidad;

    @NotNull(message = "El tipo de validacion es obligatoria.")
    private TipoValidacion tipoValidacion;
}
