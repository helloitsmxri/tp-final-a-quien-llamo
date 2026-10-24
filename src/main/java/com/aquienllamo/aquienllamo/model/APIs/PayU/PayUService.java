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
import java.util.Objects;

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
    private PayUResponseDTO mapearRespuesta(Map response) {
        PayUResponseDTO dto = new PayUResponseDTO();
        if (response == null) return dto;

        dto.setCode((String) response.get("code"));

        // Validamos que exista la clave Y que su valor no sea null
        if (response.containsKey("transactionResponse") && response.get("transactionResponse") != null) {
            Map<String, Object> transactionResponse = (Map<String, Object>) response.get("transactionResponse");

            dto.setTransactionId((String) transactionResponse.get("transactionId"));
            dto.setOrderId((String) transactionResponse.get("orderId"));
            dto.setState((String) transactionResponse.get("state"));
            dto.setResponseCode((String) transactionResponse.get("responseCode"));
            dto.setResponseMessage((String) transactionResponse.get("responseMessage"));
        } else if (response.containsKey("error")) {
            // Opcional: captura de errores directos de PayU si los envía
            dto.setResponseMessage((String) response.get("error"));
        }

        return dto;
    }

    public PayUResponseDTO procesarPago(PagoDTORequest request, String referenceCode) {

        String montoStr = request.getMonto() != null ? request.getMonto().toString() : "0";
        String firma = generarFirma(referenceCode, montoStr, Objects.requireNonNullElse(request.getCurrency(), "ARS"));

        Map<String, Object> body = Map.of(
                "language", "es",
                "command", "SUBMIT_TRANSACTION",
                "merchant", Map.of(
                        "apiKey", Objects.requireNonNullElse(payUProperties.getApiKey(), ""),
                        "apiLogin", Objects.requireNonNullElse(payUProperties.getApiLogin(), "")
                ),
                "transaction", Map.of(
                        "order", Map.of(
                                "accountId", Objects.requireNonNullElse(payUProperties.getAccountId(), ""),
                                "referenceCode", Objects.requireNonNullElse(referenceCode, ""),
                                "description", "Pago trabajo " + Objects.requireNonNullElse(request.getUuidTrabajo(), ""),
                                "language", "es",
                                "signature", firma,
                                "additionalValues", Map.of(
                                        "TX_VALUE", Map.of(
                                                "value", Objects.requireNonNullElse(request.getMonto(), 0),
                                                "currency", Objects.requireNonNullElse(request.getCurrency(), "ARS")
                                        )
                                ),
                                "buyer", Map.of(
                                        "fullName", Objects.requireNonNullElse(request.getBuyerFullName(), ""),
                                        "emailAddress", Objects.requireNonNullElse(request.getBuyerEmail(), ""),
                                        "contactPhone", Objects.requireNonNullElse(request.getBuyerPhone(), ""),
                                        "dniNumber", Objects.requireNonNullElse(request.getBuyerDNI(), "")
                                )
                        ),
                        "type", "AUTHORIZATION_AND_CAPTURE",
                        "paymentMethod", Objects.requireNonNullElse(request.getFranquicia(), ""),
                        "paymentCountry", "AR",
                        "deviceSessionId", Objects.requireNonNullElse(referenceCode, ""),
                        "creditCard", Map.of(
                                "number", Objects.requireNonNullElse(request.getNumeroTarjeta(), ""),
                                "securityCode", Objects.requireNonNullElse(request.getCvv(), ""),
                                "expirationDate", Objects.requireNonNullElse(request.getVencimientoTarjeta(), ""),
                                "name", Objects.requireNonNullElse(request.getFranquicia(), "")
                        )
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
