package com.aquienllamo.aquienllamo.entidades.rubro.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class RubroDTOResponse {

    private String uuidRubro;

    private String nombreRubro;

    private List<String> especialidades;

}
