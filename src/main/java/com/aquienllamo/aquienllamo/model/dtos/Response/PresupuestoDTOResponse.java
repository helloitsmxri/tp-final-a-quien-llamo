package com.aquienllamo.aquienllamo.model.dtos.Response;


import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PresupuestoDTOResponse {

    private Integer idPresupuesto; // así el front lo puede usar

    // Datos del emisor/receptor
    // TÉCNICO:
    private String nombreTecnico;
    private String apellidoTecnico;

    // USUARIO:
    private String nombreUsuario;
    private String apellidoUsuario;

    private BigDecimal precioEstimado;
    private String descripcionPresupuesto;
}
