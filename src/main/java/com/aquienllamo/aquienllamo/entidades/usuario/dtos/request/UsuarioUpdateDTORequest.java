package com.aquienllamo.aquienllamo.entidades.usuario.dtos.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UsuarioUpdateDTORequest {

    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String sobreMi;
    private MultipartFile foto;
}
