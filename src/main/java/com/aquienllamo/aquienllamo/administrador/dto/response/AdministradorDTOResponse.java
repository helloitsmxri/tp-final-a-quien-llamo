package com.aquienllamo.aquienllamo.administrador.dto.response;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdministradorDTOResponse {
    private String uuid;
    private String nombreUsuario;
    private String apellidoUsuario;
    private String emailUsuario;
}
