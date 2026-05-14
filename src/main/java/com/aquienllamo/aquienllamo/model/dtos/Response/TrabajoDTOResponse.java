package com.aquienllamo.aquienllamo.model.dtos.Response;

import com.aquienllamo.aquienllamo.model.Enum.EstadoTrabajo;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class TrabajoDTOResponse {

    private String uuid; //id del trabajo

    private String uuidPresupuesto; //id del presupuesto elegido

    private String descripcionTrabajo; //descripcion del trabajo

    private LocalDate fechaEstimadaInicio;

    private LocalDate fechaEstimadaFin;

    private EstadoTrabajo estadoTrabajo;
}
