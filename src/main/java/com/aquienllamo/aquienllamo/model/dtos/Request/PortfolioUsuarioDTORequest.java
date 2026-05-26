package com.aquienllamo.aquienllamo.model.dtos.Request;

import com.aquienllamo.aquienllamo.model.Enum.EstadoVerificacion;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

//que datos manda el usuario para el backend, en este caso el usuario esta creando el portfolio
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PortfolioUsuarioDTORequest {

    @NotBlank(message = "Este campo no debe quedar vacio.")
    private String uuidEspecialidad; //esto se supone que en un futuro nosotros vamos a mostrar el nombre
                                  //al usuario y lo que elija es el numero

    @NotBlank(message = "Este campo no debe quedar vacio.")
    @Size(max = 5000, message = "La nota no debe de ser de mas de 5000 caracteres.")
    private String notasAspirante;

    @NotBlank(message = "Este campo no debe quedar vacio.")
    @Size(max = 1000, message = "El enlace no debe de ser de mas de 1000 caracteres.")
    private String enlaceExterno;

    @NotBlank(message = "Este campo no debe quedar vacio.")
    @Size(max = 100, message = "El tipo de archivo no debe de ser de mas de 100 caracteres.")
    private String tipoArchivo;

    private byte[] archivoAdjunto;

}
