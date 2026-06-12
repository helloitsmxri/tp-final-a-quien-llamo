package com.aquienllamo.aquienllamo.model.APIs.MercadoPago;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.TYPE)          // se aplica sobre una clase
@Retention(RetentionPolicy.RUNTIME) // disponible en tiempo de ejecución
@Constraint(validatedBy = PagoValidator.class) // quién hace la validación
public @interface ValidPago {
    String message() default "Datos de pago incompletos";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
