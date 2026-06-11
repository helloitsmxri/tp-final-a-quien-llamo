package com.aquienllamo.aquienllamo.model.APIs.PayU;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "payu")
@Getter
@Setter
public class PayUProperties {

    private String apiKey;
    private String apiLogin;
    private String accountId;
    private String merchantId;
    private String url;

}
