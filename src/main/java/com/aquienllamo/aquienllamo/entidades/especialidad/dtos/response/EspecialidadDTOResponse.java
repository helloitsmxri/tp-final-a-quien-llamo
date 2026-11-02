package com.aquienllamo.aquienllamo.entidades.especialidad.dtos.response;

import com.aquienllamo.aquienllamo.administrador.enums.TipoValidacion;
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
