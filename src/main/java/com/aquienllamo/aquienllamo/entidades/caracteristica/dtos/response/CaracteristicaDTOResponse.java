package com.aquienllamo.aquienllamo.entidades.caracteristica.dtos.response;

import com.aquienllamo.aquienllamo.entidades.caracteristica.enums.TipoCaracteristicaE;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CaracteristicaDTOResponse {

    private String uuid; // p/el front
    private String valorAdjetivo;
    private TipoCaracteristicaE tipo;

}
