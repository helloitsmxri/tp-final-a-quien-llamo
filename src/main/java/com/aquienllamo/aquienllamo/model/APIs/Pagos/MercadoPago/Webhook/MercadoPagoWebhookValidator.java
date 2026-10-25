package com.aquienllamo.aquienllamo.model.APIs.Pagos.MercadoPago.Webhook;


import com.aquienllamo.aquienllamo.model.APIs.Pagos.MercadoPago.MercadoPagoProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MercadoPagoWebhookValidator {

    private final MercadoPagoProperties properties;

    public boolean validarFirma(
            String xSignature,
            String xRequestId,
            String dataId) {

        if (xSignature == null || xRequestId == null
                || dataId == null || properties.getWebhookSecret() == null
                || properties.getWebhookSecret().isBlank()) {
            return false;
        }

        Map<String, String> valores = new HashMap<>();

        for (String parte : xSignature.split(",")) {
            String[] entrada = parte.trim().split("=", 2);

            if (entrada.length == 2) {
                valores.put(entrada[0], entrada[1]);
            }
        }

        String timestamp = valores.get("ts");
        String firmaRecibida = valores.get("v1");

        if (timestamp == null || firmaRecibida == null) {
            return false;
        }

        // Manifest especificado por Mercado Pago.
        String manifest = "id:" + dataId
                + ";request-id:" + xRequestId
                + ";ts:" + timestamp + ";";

        try {
            Mac mac = Mac.getInstance("HmacSHA256");

            SecretKeySpec clave = new SecretKeySpec(
                    properties.getWebhookSecret()
                            .getBytes(StandardCharsets.UTF_8),
                    "HmacSHA256"
            );

            mac.init(clave);

            byte[] firmaCalculada = mac.doFinal(
                    manifest.getBytes(StandardCharsets.UTF_8)
            );

            byte[] firmaEsperada = hexToBytes(firmaRecibida);

            return MessageDigest.isEqual(
                    firmaCalculada,
                    firmaEsperada
            );

        } catch (Exception e) {
            return false;
        }
    }

    private byte[] hexToBytes(String hexadecimal) {
        if (hexadecimal == null
                || hexadecimal.length() % 2 != 0
                || !hexadecimal.matches("[0-9a-fA-F]+")) {
            return new byte[0];
        }

        byte[] resultado = new byte[hexadecimal.length() / 2];

        for (int i = 0; i < hexadecimal.length(); i += 2) {
            resultado[i / 2] = (byte) Integer.parseInt(
                    hexadecimal.substring(i, i + 2), 16
            );
        }

        return resultado;
    }
}