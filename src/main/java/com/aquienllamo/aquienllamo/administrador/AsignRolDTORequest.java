package com.aquienllamo.aquienllamo.administrador;

import com.aquienllamo.aquienllamo.auth.permissions.RolesUser;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AsignRolDTORequest {
    @NotBlank(message = "Se requiere un id de usuario")
    private String uuid;
    
    @NotBlank(message = "Elija un rol a asignar")
    private RolesUser Rol;
}
