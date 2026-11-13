package com.aquienllamo.aquienllamo.entidades.trabajo.dto.response;

import com.aquienllamo.aquienllamo.entidades.trabajo.enums.EstadoTrabajo;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class TrabajoDTOResponse {

    private String uuid; //id del trabajo

    // --- del presupuesto ---
    private String uuidPresupuesto;
    private String nombreProyecto;
    private BigDecimal precioEstimado;
    private String uuidChat; // para ir al chat de este trabajo

    // --- cliente ---
    private String uuidCliente;
    private String nombreCliente;
    private String apellidoCliente;

    // --- técnico ---
    private String uuidUsuarioTecnico; // uuid del USUARIO del técnico (para saber "si soy yo")
    private String nombreTecnico;
    private String apellidoTecnico;

    // --- del trabajo ---
    private String descripcionTrabajo;
    private LocalDate fechaEstimadaInicio;
    private LocalDate fechaEstimadaFin;
    private EstadoTrabajo estadoTrabajo;
}