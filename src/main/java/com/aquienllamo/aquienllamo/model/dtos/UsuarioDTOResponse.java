package com.aquienllamo.aquienllamo.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

// lo q devuelvo al usuario o lo q el service
// devuelve al front.
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDTOResponse {
    // El ID es fundamental para el manejo en el Front
    private Integer idUsuario;

    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String sobreMi;

    // acá con el service debería guardarse la info de q va.
    private String tipoImagen;
    private byte[] foto;

    private LocalDate fechaRegistro;
    // sirve para el online, offline...
    private LocalDateTime ultimaConexion;
}
