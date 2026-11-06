package com.aquienllamo.aquienllamo.entidades.certificacion.dtos.response;

import com.aquienllamo.aquienllamo.administrador.enums.EstadoVerificacion;
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

    private String titulo;

    // Técnico asociado
    private String nombreTecnico;
    private String apellidoTecnico;

    // Especialidad que respalda la certificación (null en las cargadas antes de este cambio)
    private String uuidEspecialidad;
    private String nombreEspecialidad;

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
