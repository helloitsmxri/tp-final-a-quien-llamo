package com.aquienllamo.aquienllamo.model.dtos.Response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder

//Response: lo que YO le devuelvo al usuario como respuesta, lo que vería.
public class UbicacionDTOResponse {
    private String uuid;
    private String codigoPostal;
    private String provincia;
    private String ciudad;
    private String calle;
    private Integer numero;
    private Integer piso;
    private Integer numeroPiso;

}
