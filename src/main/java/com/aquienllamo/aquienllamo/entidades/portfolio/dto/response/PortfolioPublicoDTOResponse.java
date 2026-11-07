package com.aquienllamo.aquienllamo.entidades.portfolio.dto.response;

import lombok.*;

// Lo que ve cualquier usuario de un portfolio: solo el título (nada de notas, enlace, estado ni archivo)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioPublicoDTOResponse {
    private String uuid;
    private String titulo;
}