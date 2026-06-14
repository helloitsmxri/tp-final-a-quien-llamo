package com.aquienllamo.aquienllamo.model.APIs.Abstract;
import org.springframework.beans.factory.annotation.Value;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class PhoneValidationService {

    private final RestTemplate restTemplate;
    @Value("${abstract.api.key}") //inyecta
    private String apiKey;
    @Value("${abstract.api.url}") //inyecta
    private String apiUrl;

    public com.aquienllamo.aquienllamo.model.APIs.Abstract.PhoneValidationResponse validarTelefono(String telefono){
        // se construye acá la url le paso la info q se inyectó y la qrecibe de afuera
        String url = apiUrl + "?api_key=" + apiKey + "&phone=" + telefono;

        return restTemplate.getForObject(url, com.aquienllamo.aquienllamo.model.APIs.Abstract.PhoneValidationResponse.class);
    }
}