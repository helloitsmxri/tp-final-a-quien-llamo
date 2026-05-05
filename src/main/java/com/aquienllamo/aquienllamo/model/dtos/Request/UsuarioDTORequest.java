package com.aquienllamo.aquienllamo.model.dtos.Request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

// es lo que le pido al usuario.
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
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

    @NotBlank (message = "El correo es obligatorio.")
    @Email (message = "El formato del correo no es correcto.")
    private String email;

    @NotBlank (message = "La clave es obligatoria.")
    @Size(min = 8, message = "Debe contener al menos 8 caracteres.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "La clave debe tener al menos una mayúscula, una minúscula, un número y un carácter especial")
    private String clave;

    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "^\\d{10}$", message = "El teléfono debe tener 10 dígitos. Evitar guiones y espacios.")
    private String telefono;

    @NotBlank(message = "Es necesario poner la fecha de nacimiento.")
    @Past(message = "La fecha de nacimiento no puede ser posterior a la actual.")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate fechaNacimiento;

    private MultipartFile foto; // es multipart NO BYTE porque multipart es de spring y sabe sacar bytes y tipos solito

    @NotBlank(message = "Es necesario añadir una breve descripción. Cuéntanos algo interesante.")
    private String sobreMi;
}
