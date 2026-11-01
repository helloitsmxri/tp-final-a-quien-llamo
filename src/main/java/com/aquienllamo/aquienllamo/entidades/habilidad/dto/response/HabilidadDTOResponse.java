package com.aquienllamo.aquienllamo.entidades.habilidad.dto.response;

import lombok.*;


import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HabilidadDTOResponse {
    private String uuid;
    private String nombreHabilidad;
    private List<String> especialidades;
}
