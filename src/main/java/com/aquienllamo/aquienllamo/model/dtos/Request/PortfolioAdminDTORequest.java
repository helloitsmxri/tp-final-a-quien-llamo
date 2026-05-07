package com.aquienllamo.aquienllamo.model.dtos.Request;

import com.aquienllamo.aquienllamo.model.Enum.EstadoVerificacion;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@ToString
@Builder
public class PortfolioAdminDTORequest {
    //lo que el admin le manda al usuario
    private EstadoVerificacion estadoVerificacion;
    private String notasAdmin;
}
