package com.aquienllamo.aquienllamo.model.dtos.Response;

import com.aquienllamo.aquienllamo.model.Enum.EstadoDenunciaE;
import com.aquienllamo.aquienllamo.model.entities.AdministradorEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DenunciaDTOResponse {
    private String uuid;

    private String nombreAdministrador;

    private String nombreDenunciante;
    private String apellidoDenunciante;
    private String emailDenunciante;

    private String nombreDenunciado;
    private String apellidoDenunciado;
    private String emailDenunciado;

    private EstadoDenunciaE estadoDenuncia;

    private String motivoDenuncia;
    private String notaDelAdmin;

    private LocalDateTime fechaDenuncia;

}
