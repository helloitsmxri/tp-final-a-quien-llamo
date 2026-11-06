package com.aquienllamo.aquienllamo.entidades.portfolio.dto.response;

import com.aquienllamo.aquienllamo.administrador.enums.EstadoVerificacion;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PortfolioUsuarioDTOResponse {

    private String uuid;
    private String titulo;

    private String uuidEspecialidad;
    private String nombreEspecialidad;

    private String notasAspirante;
    private String enlaceExterno;
    private String tipoArchivo;
    private String archivoAdjunto;

    private EstadoVerificacion estadoVerificacion;
    private String notasAdmin;
    private LocalDateTime fechaEntrega;
}