package com.aquienllamo.aquienllamo.model.dtos.Response;

import com.aquienllamo.aquienllamo.model.Enum.EstadoVerificacion;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.*;

//Existe un DTO por caso de uso / tipo de usuario, el usuario solo debe ver si esta
//aprobado o no y si tiene notas del admin

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PortfolioUsuarioDTOResponse {

    private EstadoVerificacion estadoVerificacion;
    private String notasAdmin;

}
