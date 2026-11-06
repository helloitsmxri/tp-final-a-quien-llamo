package com.aquienllamo.aquienllamo.entidades.portfolio.dto.response;

import com.aquienllamo.aquienllamo.administrador.enums.EstadoVerificacion;
import lombok.*;

import java.time.LocalDateTime;

//Existe un DTO por caso de uso / tipo de usuario, el usuario solo debe ver si esta
//aprobado o no y si tiene notas del admin

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PortfolioUsuarioDTOResponse {

    private String uuid;
    private String titulo;
    private String nombreEspecialidad;
    private String notasAspirante;
    private String enlaceExterno;
    private String tipoArchivo;

    private EstadoVerificacion estadoVerificacion;
    private String notasAdmin;
    private LocalDateTime fechaEntrega;

}
