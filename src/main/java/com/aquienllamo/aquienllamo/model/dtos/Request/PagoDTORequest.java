package com.aquienllamo.aquienllamo.model.dtos.Request;

import com.aquienllamo.aquienllamo.model.APIs.MercadoPago.ValidPago;
import com.aquienllamo.aquienllamo.model.Enum.MetodoDePago;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@ValidPago
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

    @NotNull(message = "Debe ingresar el monto a pagar.")
    @Min(value = 1, message = "El valor debe ser superior a 1")
    private BigDecimal monto;

    @NotBlank(message = "Debe ingresar la moneda que se utilizara para pagar.")
    private String currency; // "ARS"

    private String numeroTarjeta;
    private String cvv;
    private String vencimientoTarjeta;  // formato "YYYY/MM"
    private String franquicia;

}
