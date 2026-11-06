package com.aquienllamo.aquienllamo.entidades.tecnico.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class TecnicoEditarDTORequest {
    // Datos personales
    private String nombre;
    private String apellido;
    private String telefono;
    private String sobreMi;
    private MultipartFile foto;

    // Datos profesionales
    private String cuit;
    private String descripcionTrabajo;
    private String proyectos;

    private List<String> uuidHabilidades;
    private List<String> uuidEspecialidades;
}
