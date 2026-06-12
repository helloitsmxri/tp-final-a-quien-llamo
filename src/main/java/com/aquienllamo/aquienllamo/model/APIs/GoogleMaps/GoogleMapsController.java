package com.aquienllamo.aquienllamo.model.APIs.GoogleMaps;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GoogleMapsController {

    private final GoogleMapsService googleMapsService;

    @GetMapping("/maps/geocode")
    public GoogleMapsResponseDTO geocode(@RequestParam String direccion) {
        return googleMapsService.geocodificar(direccion);
    }
}
