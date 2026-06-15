package com.aquienllamo.aquienllamo.model.dtos.Response;

import com.aquienllamo.aquienllamo.model.Enum.TipoCaracteristicaE;
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
