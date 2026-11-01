package com.aquienllamo.aquienllamo.entidades.denuncia.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DenunciaDTORequest {

    @NotBlank(message = "Debe incluir el motivo por el cual está denunciando.")
    private String motivoDenuncia;

    private MultipartFile foto;
}
