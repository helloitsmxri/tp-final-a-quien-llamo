package com.aquienllamo.aquienllamo.model.dtos.Request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TecnicoDTORequest {

    @NotBlank(message = "El cuit es obligatorio.")
    @Size(min = 11,  max = 11, message = "El CUIT debe tener 11 caracteres.")
    @Pattern(regexp = "^\\d{11}$", message = "El CUIT debe contener solo números.")
    private String cuit;

    @Size(min = 10, message = "La descripción debe tener al menos 10 caracteres.")
    private String descripcionTrabajo;

    @Size(min = 10, max = 5000, message = "Los proyectos deben tener entre 10 y 5000 caracteres.")
    private String proyectos;

    @NotEmpty(message = "Debe seleccionar al menos una habilidad.")
    private List<Integer> idHabilidades;
    
    @NotEmpty(message = "Debe seleccionar al menos una especialidad.")
    private List <Integer> idEspecialidades;

}
