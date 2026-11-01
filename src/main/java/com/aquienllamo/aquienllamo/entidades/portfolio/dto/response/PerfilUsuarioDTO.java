package com.aquienllamo.aquienllamo.entidades.usuario.dtos.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PerfilUsuarioDTO {
    private String uuid;

    private String nombre;
    private String apellido;

    private String email;
    private String telefono;

    private String sobreMi;

    private String fotoBase64;
    private String tipoImagen;

    private String tipoUsuario;

    private LocalDateTime fechaRegistro;
    private LocalDateTime ultimaActividad;
}
