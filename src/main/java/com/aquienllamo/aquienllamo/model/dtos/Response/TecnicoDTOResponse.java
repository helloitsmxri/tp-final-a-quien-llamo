package com.aquienllamo.aquienllamo.model.dtos.Response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TecnicoDTOResponse {
    private String uuid;

    // Datos del usuario asociado
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String sobreMi;
    private String fotoBase64;
    private String tipoImagen;

    // Datos del técnico
    private String cuit;
    private String descripcionTrabajo;
    private String proyectos;

    // Listas de habilidades y especialidades
    private List<String> habilidades;
    private List<String> especialidades;
}
