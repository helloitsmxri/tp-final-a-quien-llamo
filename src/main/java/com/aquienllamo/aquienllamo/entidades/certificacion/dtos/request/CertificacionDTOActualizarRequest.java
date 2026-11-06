package com.aquienllamo.aquienllamo.entidades.certificacion.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CertificacionDTOActualizarRequest {

    private String uuidEspecialidad;

    @NotBlank(message = "El título es obligatorio.")
    @Size(max = 150, message = "El título no puede superar los 150 caracteres.")
    private String titulo;

    @NotBlank(message = "El número de matrícula es obligatorio.")
    @Size(max = 100, message = "El número de matrícula no puede superar los 100 caracteres.")
    private String numMatricula;

    @NotBlank(message = "El ente otorgador es obligatorio.")
    @Size(max = 100, message = "El ente otorgador no puede superar los 100 caracteres.")
    private String enteOtorgador;

    @NotNull(message = "La fecha de vencimiento es obligatoria.")
    private LocalDate fechaVencimiento;

    // Opcional al actualizar: si no se envía, se conserva el archivo actual.
    private MultipartFile imagenCertificado;
}