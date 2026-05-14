package com.aquienllamo.aquienllamo.model.dtos.Response;

import lombok.*;


import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HabilidadDTOResponse {
    private String uuid;
    private String nombreHabilidad;
    private List<String> especialidades;
}
