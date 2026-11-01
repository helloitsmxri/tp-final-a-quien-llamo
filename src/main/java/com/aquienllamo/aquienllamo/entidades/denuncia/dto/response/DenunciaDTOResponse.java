package com.aquienllamo.aquienllamo.entidades.denuncia.dto.response;

import com.aquienllamo.aquienllamo.entidades.denuncia.enums.EstadoDenunciaE;
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
