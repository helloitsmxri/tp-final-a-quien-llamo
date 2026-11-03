package com.aquienllamo.aquienllamo.entidades.presupuesto.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PresupuestoDetalleDTORequest {

    @NotBlank(message = "La descripción del concepto es obligatoria.")
    @Size(max = 500, message = "La descripción no puede superar los 500 caracteres")
    private String descripcion;

    @NotNull(message = "Es necesario ingresar un precio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a cero")
    @Digits(integer = 8, fraction = 2, message = "El precio no tiene un formato válido")
    private BigDecimal precioUnitario;

    @NotNull(message = "Es necesario ingresar una cantidad")
    @Min(value = 1, message = "La cantidad debe ser como mínimo 1")
    private Integer cantidad;

}
