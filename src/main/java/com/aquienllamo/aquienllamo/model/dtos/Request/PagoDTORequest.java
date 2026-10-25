package com.aquienllamo.aquienllamo.model.dtos.Request;

import com.aquienllamo.aquienllamo.model.Enum.MetodoDePago;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PagoDTORequest {

    @NotBlank(message = "Debe ingresar el trabajo que va a pagar.")
    private String uuidTrabajo;

    @NotNull(message = "Debe seleccionar un metodo de pago")
    private MetodoDePago metodoDePago;

    //datos ddel comprobador para la api PayU

    @NotBlank(message = "Debe ingresar el nombre completo del comprobador.")
    private String buyerFullName;

    @NotBlank(message = "Debe ingresar el email del comprobador.")
    @Email
    private String buyerEmail;

    @NotBlank(message = "Debe ingresar el DNI del comprobador.")
    private String buyerDNI;

    @NotBlank(message = "Debe ingresar el telefono del comprobador")
    private String buyerPhone;

}
