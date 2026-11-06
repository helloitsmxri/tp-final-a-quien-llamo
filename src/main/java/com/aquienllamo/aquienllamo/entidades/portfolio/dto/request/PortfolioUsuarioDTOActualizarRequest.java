
package com.aquienllamo.aquienllamo.entidades.portfolio.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PortfolioUsuarioDTOActualizarRequest {

    @NotBlank(message = "El título no debe quedar vacío.")
    @Size(max = 150, message = "El título no puede superar los 150 caracteres.")
    private String titulo;

    @NotBlank(message = "La nota no debe quedar vacía.")
    @Size(max = 5000, message = "La nota no puede superar los 5000 caracteres.")
    private String notasAspirante;

    @Size(max = 1000, message = "El enlace no puede superar los 1000 caracteres.")
    private String enlaceExterno;

    @Size(max = 100, message = "El tipo de archivo no puede superar los 100 caracteres.")
    private String tipoArchivo;

    private byte[] archivoAdjunto;
}
