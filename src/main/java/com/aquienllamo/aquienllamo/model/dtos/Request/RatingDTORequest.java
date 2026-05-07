package com.aquienllamo.aquienllamo.model.dtos.Request;

import com.aquienllamo.aquienllamo.model.entities.CaracteristicaEntity;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class RatingDTORequest {

    @NotNull
    private Integer usuarioDestinatario; //a quien le manda la reseña

    @NotNull(message = "Debe ingresar una valoracion")
    private Integer valoracion;

    private List<Integer> caracteristicas; //caracteristicas tipo "puntual"

    private String descripcion; //puede no escribir nada y solo subir una nota

}
