
package com.aquienllamo.aquienllamo.model.APIs.Pagos.MercadoPago;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MercadoPagoOrderStatusResponse {

    private String id;
    private String status;

    @JsonProperty("status_detail")
    private String statusDetail;

    @JsonProperty("external_reference")
    private String externalReference;
}
