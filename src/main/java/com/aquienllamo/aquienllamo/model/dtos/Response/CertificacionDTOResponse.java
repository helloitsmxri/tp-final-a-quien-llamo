package com.aquienllamo.aquienllamo.model.dtos.Response;

import com.aquienllamo.aquienllamo.model.Enum.EstadoVerificacion;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CertificacionDTOResponse {
    private String uuid;

    // Técnico asociado
    private String nombreTecnico;
    private String apellidoTecnico;

    // Datos de la certificación
    private String numMatricula;
    private String enteOtorgador;
    private LocalDate fechaVencimiento;

    // Imagen
    private String imagenBase64;
    private String tipoImagen;

    private EstadoVerificacion estadoVerificacion;
    private String notasAdmin;


}
