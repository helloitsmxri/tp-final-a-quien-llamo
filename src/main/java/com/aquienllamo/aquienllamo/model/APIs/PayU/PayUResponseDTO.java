package com.aquienllamo.aquienllamo.model.APIs.PayU;

import lombok.Data;

@Data
public class PayUResponseDTO {

    private String code;
    private String transactionId;
    private String orderId;
    private String state;
    private String responseCode;
    private String responseMessage;

}
