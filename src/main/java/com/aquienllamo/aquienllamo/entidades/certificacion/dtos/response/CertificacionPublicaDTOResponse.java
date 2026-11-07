package com.aquienllamo.aquienllamo.entidades.certificacion.dtos.response;

import lombok.*;

// Lo que ve cualquier usuario de una certificación: solo el título (nada de matrícula, estado ni archivo)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CertificacionPublicaDTOResponse {
    private String uuid;
    private String titulo;
}