package com.aquienllamo.aquienllamo.APIs.Pagos.MetodosPago;

import com.aquienllamo.aquienllamo.entidades.pago.enums.MetodoDePago;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MetodoPagoFactory {

    private final List<MetodoPago> estrategias;

    public MetodoPagoFactory(
            List<MetodoPago> estrategias) {
        this.estrategias = estrategias;
    }

    public MetodoPago obtener(
            MetodoDePago metodo) {

        return estrategias.stream()
                .filter(estrategia -> estrategia.soporta(metodo))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Método de pago no soportado: " + metodo
                        )
                );
    }
}
