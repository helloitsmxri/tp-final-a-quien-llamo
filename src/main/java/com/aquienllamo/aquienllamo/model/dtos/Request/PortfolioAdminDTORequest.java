package com.aquienllamo.aquienllamo.model.dtos.Request;

import com.aquienllamo.aquienllamo.model.Enum.EstadoVerificacion;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "Este campo no debe quedar vacio.")
    @Size(max = 2000, message = "Las notas no pueden superar los 2000 caracteres.")
    private String notasAdmin;
}
