package com.aquienllamo.aquienllamo.model.APIs.GoogleMaps;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GoogleMapsService {

    private final GoogleMapsProperties googleMapsProperties;
    private final RestClient restClient = RestClient.create();

    @SuppressWarnings("unchecked")
    public GoogleMapsResponseDTO geocodificar(String direccion) {

        Map response = restClient.get()
                .uri(googleMapsProperties.getUrl()
                        + "?address=" + direccion
                        + "&key=" + googleMapsProperties.getApiKey())
                .retrieve()
                .body(Map.class);

        if (response == null || !"OK".equals(response.get("status"))) {
            throw new RuntimeException("ERROR: no se pudo geocodificar la direccion");
        }

        List<Map> results = (List<Map>) response.get("results");
        Map primerResultado = results.get(0);

        Map geometry = (Map) primerResultado.get("geometry");
        Map location = (Map) geometry.get("location");

        GoogleMapsResponseDTO dto = new GoogleMapsResponseDTO();
        dto.setLatitud((Double) location.get("lat"));
        dto.setLongitud((Double) location.get("lng"));
        dto.setDireccionFormateada((String) primerResultado.get("formatted_address"));

        return dto;
    }
}
