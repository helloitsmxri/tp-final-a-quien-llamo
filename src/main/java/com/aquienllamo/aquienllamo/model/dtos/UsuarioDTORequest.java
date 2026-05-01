package com.aquienllamo.aquienllamo.model.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// es lo que le pido al usuario.
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDTORequest {
    // Datos personales básicos
    @NotBlank(message = "El nombre es obligatorio.")
    @Size(min = 2, message = "El nombre debe tener al menos dos caracteres.")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio.")
    @Size(min = 2, message = "El nombre debe tener al menos dos caracteres.")
    private String apellido;

    @NotBlank(message = "Es obligatorio ingresar el DNI.")
    @Size(min = 8, max = 8, message = "El DNI debe tener 8 caracteres.")
    private String dni;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 8)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "La clave debe tener al menos una mayúscula, una minúscula, un número y un carácter especial")
    private String clave;

    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "^\\d{10}$", message = "El teléfono debe tener 10 dígitos. Evitar guiones y espacios.")
    private String telefono;

    private String sobreMi;
}
