package com.aquienllamo.aquienllamo.model.dtos.Request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor // constructor completo
@NoArgsConstructor // constructor vacío
@ToString

//Request: es lo que el usuario me manda.
public class UbicacionDTORequest {
    private String codigoPostal;
    private String provincia;
    private String ciudad;
    private String calle;
    private Integer numero;
    private Integer piso;
    private Integer numeroPiso;



}
