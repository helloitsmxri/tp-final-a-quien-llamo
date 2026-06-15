package com.aquienllamo.aquienllamo.model.dtos.Response;

import com.aquienllamo.aquienllamo.model.Enum.EstadoVerificacion;
import lombok.*;

import java.time.LocalDateTime;

//el admin debe evaluar todo lo que ingresa el usuario, por eso debe poder ver todo

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PortfolioAdminDTOResponse {

    private String uuid; //uuid del portfolio

    private String uuidTecnico;
    private String nombreTecnico;

    private String nombreEspecialidad;

    private EstadoVerificacion estadoVerificacion;

    private String notasAspirante;
    private String enlaceExterno;
    private String tipoArchivo;

    private String notasAdmin;
    private LocalDateTime fechaEntrega;

}
