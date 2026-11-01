package com.aquienllamo.aquienllamo.entidades.especialidad.dtos.response;

import com.aquienllamo.aquienllamo.administrador.TipoValidacion;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EspecialidadCreadaDTOResponse {
    private String uuid;
    private String nombreEspecialidad;
    private TipoValidacion tipoValidacion;
}
