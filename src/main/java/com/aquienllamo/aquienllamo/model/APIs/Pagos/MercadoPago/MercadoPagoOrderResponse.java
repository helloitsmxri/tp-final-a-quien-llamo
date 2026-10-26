
package com.aquienllamo.aquienllamo.model.APIs.Pagos.MercadoPago;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MercadoPagoOrderResponse {

    private String id;

    private String status;

    @JsonProperty("checkout_url")
    private String checkoutUrl;
}

