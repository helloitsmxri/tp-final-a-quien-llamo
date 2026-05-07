package com.aquienllamo.aquienllamo.model.dtos.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CertificacionDTORequest {

    @NotBlank(message = "El número de matrícula es obligatorio.")
    @Size(max = 100, message = "El número de matrícula no puede superar los 100 caracteres.")
    private String numMatricula;

    @NotBlank(message = "El ente otorgador es obligatorio.")
    @Size(max = 100, message = "El ente otorgador no puede superar los 100 caracteres.")
    private String enteOtorgador;

    @NotNull(message = "La fecha de vencimiento es obligatoria.")
    private LocalDate fechaVencimiento;

    @NotNull(message = "La imagen del certificado es obligatoria.")
    private MultipartFile imagenCertificado;
}
