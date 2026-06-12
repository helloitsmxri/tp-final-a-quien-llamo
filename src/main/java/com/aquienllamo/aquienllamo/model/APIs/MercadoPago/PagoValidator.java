package com.aquienllamo.aquienllamo.model.APIs.MercadoPago;

import com.aquienllamo.aquienllamo.model.Enum.MetodoDePago;
import com.aquienllamo.aquienllamo.model.dtos.Request.PagoDTORequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PagoValidator implements ConstraintValidator<ValidPago, PagoDTORequest> {

    @Override
    public boolean isValid(PagoDTORequest request, ConstraintValidatorContext context) {

        if (request.getMetodoDePago() == MetodoDePago.Credito ||
                request.getMetodoDePago() == MetodoDePago.Debito) {

            boolean valido = true;

            if (isBlank(request.getNumeroTarjeta())) {
                agregarError(context, "numeroTarjeta", "Debe ingresar el número de tarjeta.");
                valido = false;
            }
            if (isBlank(request.getCvv())) {
                agregarError(context, "cvv", "Debe ingresar el CVV.");
                valido = false;
            }
            if (isBlank(request.getVencimientoTarjeta())) {
                agregarError(context, "vencimientoTarjeta", "Debe ingresar el vencimiento.");
                valido = false;
            }
            if (isBlank(request.getFranquicia())) {
                agregarError(context, "franquicia", "Debe ingresar la franquicia.");
                valido = false;
            }

            return valido;
        }

        return true;
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    private void agregarError(ConstraintValidatorContext context, String campo, String mensaje) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(mensaje)
                .addPropertyNode(campo)
                .addConstraintViolation();
    }
}
