package com.aquienllamo.aquienllamo.model.dtos.Response;

import com.aquienllamo.aquienllamo.model.Enum.EstadoDenunciaE;
import com.aquienllamo.aquienllamo.model.entities.AdministradorEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DenunciaDTOResponse {
    private String uuid;

    //Administrador
    private AdministradorEntity administrador;
    // Denunciante
    private String nombreDenunciante;
    private String apellidoDenunciante;

    // Denunciado
    private String nombreDenunciado;
    private String apellidoDenunciado;

    private EstadoDenunciaE estadoDenuncia;

    private String motivoDenuncia;
    private LocalDateTime fechaDenuncia;

}
