package com.aquienllamo.aquienllamo.model.dtos.Response;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class RatingDTOResponse {

    private String uuid; //el id del comentario

    private Integer usuarioRemitente; //del usuario remitente

    private Integer usuarioDestinatario;

    private Integer valoracion;

    private String descripcion;

    private List<String> caracteristicas;

}
