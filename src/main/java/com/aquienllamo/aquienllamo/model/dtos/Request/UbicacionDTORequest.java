package com.aquienllamo.aquienllamo.model.dtos.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor // constructor completo
@NoArgsConstructor // constructor vacío
@ToString

//Request: es lo que el usuario me manda.
public class UbicacionDTORequest {

    @NotBlank(message = "El codigo postal es obligatorio.")
    @Size(max = 10, message = "El codigo postal no puede superar los 10 caracteres.")
    private String codigoPostal;

    @NotBlank(message = "El nombre de la provincia es obligatorio.")
    @Size(max = 50, message = "El nombre de la provincia no puede superar los 50 caracteres.")
    private String provincia;

    @NotBlank(message = "El nombre de la ciudad es obligatorio.")
    @Size(max = 50, message = "El nombre de la ciudad no puede superar los 50 caracteres.")
    private String ciudad;

    @NotBlank(message = "El nombre de la calle es obligatorio.")
    @Size(max = 50, message = "El nombre de la calle no puede superar los 50 caracteres.")
    private String calle;

    @NotNull(message = "El numero de la calle es obligatorio.")
    private Integer numero;

    private Integer piso;
    private Integer numeroPiso;



}
