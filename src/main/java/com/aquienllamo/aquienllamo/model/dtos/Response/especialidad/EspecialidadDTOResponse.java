package com.aquienllamo.aquienllamo.model.dtos.Response.especialidad;

import com.aquienllamo.aquienllamo.model.Enum.TipoValidacion;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EspecialidadDTOResponse {
    private String uuid;
    private String nombreEspecialidad;
    private TipoValidacion tipoValidacion;
    private List<String> habilidades;
    private List<String> rubros;
}
