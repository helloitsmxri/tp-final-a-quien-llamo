package com.aquienllamo.aquienllamo.model.dtos.Response;


import com.aquienllamo.aquienllamo.model.Enum.EstadoPresupuestoE;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PresupuestoDTOResponse {

    private String uuid; // para el front!!

    // Datos del emisor/receptor
    // TÉCNICO:
    private String nombreTecnico;
    private String apellidoTecnico;

    // USUARIO:
    private String nombreUsuario;
    private String apellidoUsuario;

    private BigDecimal precioEstimado;
    private String descripcionPresupuesto;

    private LocalDate fechaRealizado;
    private EstadoPresupuestoE estado;
}
