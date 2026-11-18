
package com.aquienllamo.aquienllamo.APIs.Pagos.MercadoPago;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MercadoPagoOrderResponse {

    private String id;

    private String type;

    @JsonProperty("processing_mode")
    private String processingMode;

    private String status;

    @JsonProperty("status_detail")
    private String statusDetail;

    @JsonProperty("external_reference")
    private String externalReference;

    @JsonProperty("total_amount")
    private String totalAmount;

    @JsonProperty("total_paid_amount")
    private String totalPaidAmount;

    @JsonProperty("checkout_url")
    private String checkoutUrl;

    @JsonProperty("client_token")
    private String clientToken;

    private String currency;

    @JsonProperty("country_code")
    private String countryCode;
}


