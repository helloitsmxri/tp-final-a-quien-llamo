package com.aquienllamo.aquienllamo.entidades.trabajo.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.AssertTrue;
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

    @NotBlank(message = "Debe indicar el presupuesto del trabajo.")
    private String uuidPresupuesto;

    @NotBlank(message = "Describa el trabajo a realizar.")
    @Size(min = 50, max = 1000,
            message = "La descripcion debe tener minimo 50 caracteres, maximo 1000.")
    private String descripcionTrabajo;

    @NotNull(message = "Debe elegir una fecha para iniciar el trabajo.")
    private LocalDate fechaEstimadaInicio;

    @NotNull(message = "Debe elegir una fecha de finalizacion del trabajo.")
    private LocalDate fechaEstimadaFin;

    @JsonIgnore
    @AssertTrue(message = "La fecha de finalizacion no puede ser anterior a la de inicio.")
    public boolean isFechasValidas() {
        if (fechaEstimadaInicio == null || fechaEstimadaFin == null) {
            return true;
        }

        return !fechaEstimadaFin.isBefore(fechaEstimadaInicio);
    }
}
