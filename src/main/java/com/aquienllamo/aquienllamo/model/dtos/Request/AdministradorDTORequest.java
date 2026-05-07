package com.aquienllamo.aquienllamo.model.dtos.Request;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AdministradorDTORequest {
    private String nombreUsuario;
    private String clave;
}
