package com.aquienllamo.aquienllamo.model.dtos.Request;

import com.aquienllamo.aquienllamo.model.Enum.EstadoVerificacion;
import lombok.*;

//que datos manda el usuario para el backend, en este caso el usuario esta creando el portfolio
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PortfolioUsuarioDTORequest {

    private Integer especialidad; //esto se supone que en un futuro nosotros vamos a mostrar el nombre
                                  //al usuario y lo que elija es el numero
    private String notasAspirante;
    private String enlaceExterno;
    private String tipoArchivo;
    private byte[] archivoAdjunto;

}
