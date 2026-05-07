package com.aquienllamo.aquienllamo.model.dtos.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DenunciaDTORequest {

    @NotBlank(message = "Debe incluir el motivo por el cual está denunciando.")
    private String motivoDenuncia;

    private MultipartFile foto;
}
