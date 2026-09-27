package com.aquienllamo.aquienllamo.model.dtos.Request;

import com.aquienllamo.aquienllamo.model.auth.permissions.RolesUser;
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
