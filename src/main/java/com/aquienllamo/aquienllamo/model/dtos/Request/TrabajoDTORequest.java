package com.aquienllamo.aquienllamo.model.dtos.Request;

import com.aquienllamo.aquienllamo.model.Enum.EstadoTrabajo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class TrabajoDTORequest {

    @NotBlank
    private String uuidPresupuesto;

    @NotBlank(message = "Justifique el presupuesto elegido.")
    private String descripcionTrabajo;

    @NotNull(message = "Debe elegir una fecha para iniciar el trabajo.")
    private LocalDate fechaEstimadaInicio;

    @NotNull(message = "Debe elegir una fecha de finalizacion del trabajo.")
    private LocalDate fechaEstimadaFin;

    private EstadoTrabajo estadoTrabajo;
}
