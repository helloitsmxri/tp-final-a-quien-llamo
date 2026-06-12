package com.aquienllamo.aquienllamo.model.APIs.GoogleMaps;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix= "google.maps") //busca todo lo que empiece con google.maps
@Getter
@Setter
public class GoogleMapsProperties {

    private String apiKey;
    private String url;
}
