package com.aquienllamo.aquienllamo.model.dtos.Response;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.*;

import java.time.LocalDateTime;

//el admin debe evaluar todo lo que ingresa el usuario, por eso debe poder ver todo

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PortfolioAdminDTOResponse {

    private String uuid;

    private Integer tecnico;
    private Integer especialidad;

    private String notasAspirante;
    private String enlaceExterno;
    private String tipoArchivo;

    private String notasAdmin;
    private LocalDateTime fechaEntrega;

}
