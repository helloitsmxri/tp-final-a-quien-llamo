package com.aquienllamo.aquienllamo.entidades.usuario.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DeleteUsuarioDTORequest {
    @NotBlank(message = "Es necesario ingresar una clave, no puede estar vacía.")
    @Size(min = 8, message = "Debe contener al menos 8 caracteres.")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z\\d\\s]).{8,}$",
            message = "La clave debe tener al menos una mayúscula, una minúscula, un número y un carácter especial"
    )
    private String password;
}
