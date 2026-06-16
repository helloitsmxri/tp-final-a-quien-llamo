package com.aquienllamo.aquienllamo.model.dtos.Response;

import com.aquienllamo.aquienllamo.model.Enum.TipoValidacion;
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
