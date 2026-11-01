package com.aquienllamo.aquienllamo.APIs.GoogleMaps;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GoogleMapsResponseDTO {

    private double latitud;
    private double longitud;
    private String direccionFormateada;
}
