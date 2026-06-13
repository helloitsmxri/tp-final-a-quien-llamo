package com.aquienllamo.aquienllamo.model.APIs.PayU;

import com.aquienllamo.aquienllamo.model.dtos.Request.PagoDTORequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PayUService {

    private final PayUProperties  payUProperties;
    private final RestClient restClient = RestClient.create();

    private String generarFirma (String referenceCode, String monto, String currency){
        try{
            String raw = payUProperties.getApiKey() + "~" +
                    payUProperties.getMerchantId() + "~" +
                    referenceCode + "~" +
                    monto + "~" +
                    currency;
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(raw.getBytes(StandardCharsets.UTF_8));
            BigInteger number = new BigInteger(1, hash);
            StringBuilder sb = new StringBuilder(number.toString(16));
            while(sb.length() < 32) sb.insert(0, '0');
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("ERROR: no se pudo generar firma PayU",e);
        }
    }

    @SuppressWarnings("unchecked")
    private PayUResponseDTO mapearRespuesta (Map response){
        PayUResponseDTO dto = new PayUResponseDTO();
        dto.setCode((String) response.get("code"));

        if(response.containsKey("transactionResponse")){
            Map<String, Object> transactionResponse = (Map<String, Object>) response.get("transactionResponse");
            dto.setTransactionId((String) transactionResponse.get("transactionId"));
            dto.setOrderId((String) transactionResponse.get("orderId"));
            dto.setState((String) transactionResponse.get("state"));
            dto.setResponseCode((String) transactionResponse.get("responseCode"));
            dto.setResponseMessage((String) transactionResponse.get("responseMessage"));
        }
        return dto;
    }

    public PayUResponseDTO procesarPago(PagoDTORequest request,String referenceCode){

        String firma = generarFirma(referenceCode, request.getMonto().toString(),  request.getCurrency());

        Map<String, Object> body = Map.of(
                "language", "es",
                "command", "SUBMIT_TRANSACTION",
                "merchant", Map.of(
                        "apiKey", payUProperties.getApiKey(),
                        "apiLogin", payUProperties.getApiLogin()
                ),
                "transaction", Map.of(
                        "order", Map.of(
                                "accountId", payUProperties.getAccountId(),
                                "referenceCode", referenceCode,
                                "description", "Pago trabajo " + request.getUuidTrabajo(),
                                "language", "es",
                                "signature", firma,
                                "additionalValues", Map.of(
                                        "TX_VALUE", Map.of(
                                                "value", request.getMonto(),
                                                "currency", request.getCurrency()
                                        )
                                ),
                                "buyer", Map.of(
                                        "fullName", request.getBuyerFullName(),
                                        "emailAddress", request.getBuyerEmail(),
                                        "contactPhone", request.getBuyerPhone(),
                                        "dniNumber", request.getBuyerDNI()
                                )
                        ),
                        "type", "AUTHORIZATION_AND_CAPTURE",
                        "paymentMethod", "VISA",
                        "paymentCountry", "AR",
                        "deviceSessionId", referenceCode
                ),
                "test", true
        );

        Map response = restClient.post()
                .uri(payUProperties.getUrl())
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(body)
                .retrieve()
                .body(Map.class);

        return mapearRespuesta(response);

    }


}
