package com.aquienllamo.aquienllamo.entidades.tecnico.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TecnicoPerfilPublicoDTO {

    private String uuid;
    private String uuidUsuario;

    // Datos del usuario
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

    // Habilidades y especialidades
    private List<String> habilidades;
    private List<String> especialidades;
}