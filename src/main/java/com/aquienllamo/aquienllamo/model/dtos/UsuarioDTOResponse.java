package com.aquienllamo.aquienllamo.model.dtos;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

// lo q devuelvo al usuario o lo q el service
// devuelve al front.
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UsuarioDTOResponse {
    // El ID es fundamental para el manejo en el Front
    private Integer idUsuario;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String sobreMi;

    // byte puede dar errores, creo que el frontend usa base64, por lo q conviene un texto
    private String fotoBase64;
    private String tipoImagen;

    private LocalDateTime fechaRegistro;
    private LocalDateTime ultimaActividad;
}
