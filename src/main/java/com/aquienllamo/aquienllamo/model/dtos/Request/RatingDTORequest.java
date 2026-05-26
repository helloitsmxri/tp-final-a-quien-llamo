package com.aquienllamo.aquienllamo.model.dtos.Request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class RatingDTORequest {

    @NotBlank(message = "Este campo no debe quedar vacio.")
    private String uuidRemitente;

    @NotBlank(message = "Este campo no debe quedar vacio.")
    private String uuidDestinatario; //a quien le manda la reseña

    @NotNull(message = "Este campo no debe quedar vacio.")
    @Min(value = 1, message = "La valoracion minima es 0")
    @Max(value = 5, message = "La valoracion maxima es 5")
    private Integer valoracion;

    @Size(max = 2000, message = "La descripcion no puede superar los 2000 caracteres.")
    private String descripcion; //puede no escribir nada y solo subir una nota

    @Size(max = 50, message = "El tipo de foto no puede superar los 50 caracteres.")
    private String tipoFoto;

    private byte[] foto;

    @NotEmpty(message = "Debe seleccional al menos una caracteristica.")
    private List<String> uuidCaracteristicas; //caracteristicas tipo "puntual"

}
