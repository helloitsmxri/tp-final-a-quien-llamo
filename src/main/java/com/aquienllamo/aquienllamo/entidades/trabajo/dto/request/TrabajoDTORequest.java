package com.aquienllamo.aquienllamo.entidades.trabajo.dto.request;

import com.aquienllamo.aquienllamo.entidades.trabajo.enums.EstadoTrabajo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @Size(min = 50, max = 1000, message = "El mensaje debe tener minimo 50 caracteres, maximo 1000.")
    private String descripcionTrabajo;

    @NotNull(message = "Debe elegir una fecha para iniciar el trabajo.")
    private LocalDate fechaEstimadaInicio;

    @NotNull(message = "Debe elegir una fecha de finalizacion del trabajo.")
    private LocalDate fechaEstimadaFin;

    private EstadoTrabajo estadoTrabajo;
}
