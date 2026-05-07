package com.aquienllamo.aquienllamo.model.dtos.Response;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HabilidadDTOResponse {
    private String uuid;
    private String nombreHabilidad;
}
