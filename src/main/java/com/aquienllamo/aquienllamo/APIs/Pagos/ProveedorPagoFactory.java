package com.aquienllamo.aquienllamo.APIs.Pagos;

import com.aquienllamo.aquienllamo.entidades.pago.enums.EProveedorPago;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProveedorPagoFactory {

    private final List<ProveedorPago> proveedores;

    public ProveedorPago obtener(EProveedorPago proveedor) {

        return proveedores.stream()
                .filter(p -> p.soporta(proveedor))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Proveedor de pago no soportado: " + proveedor
                        )
                );
    }
}
