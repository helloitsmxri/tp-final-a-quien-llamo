package com.aquienllamo.aquienllamo.entidades.portfolio.dto.request;

import com.aquienllamo.aquienllamo.administrador.enums.EstadoVerificacion;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PortfolioAdminDTORequest {
    //lo que el admin le manda al usuario
    @NotNull(message = "Debe seleccionar un estado.")
    private EstadoVerificacion estadoVerificacion;

    @Size(max = 2000, message = "Las notas no pueden superar los 2000 caracteres.")
    private String notasAdmin;
}
