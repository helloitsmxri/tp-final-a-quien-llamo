package com.aquienllamo.aquienllamo.entidades.certificacion.mapper;

import com.aquienllamo.aquienllamo.entidades.certificacion.dtos.request.CertificacionDTORequest;
import com.aquienllamo.aquienllamo.entidades.certificacion.dtos.response.CertificacionDTOResponse;
import com.aquienllamo.aquienllamo.entidades.certificacion.entity.CertificacionEntity;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class CertificacionMapper {
    public CertificacionDTOResponse toResponse(CertificacionEntity certificacion){
        if (certificacion == null){
            return null;
        }
        String imagenBase64=null;
        if (certificacion.getImagenCertificado() != null && certificacion.getImagenCertificado().length > 0) {
            imagenBase64 = Base64.getEncoder().encodeToString(certificacion.getImagenCertificado());
        }
        return CertificacionDTOResponse.builder()
                .uuid(certificacion.getUuid())
                .titulo(certificacion.getTitulo())
                .nombreTecnico(certificacion.getTecnico().getUsuario().getNombre())
                .apellidoTecnico(certificacion.getTecnico().getUsuario().getApellido())
                .uuidEspecialidad(certificacion.getEspecialidad() != null ? certificacion.getEspecialidad().getUuid() : null)
                .nombreEspecialidad(certificacion.getEspecialidad() != null ? certificacion.getEspecialidad().getNombreEspecialidad() : null)
                .numMatricula(certificacion.getNumMatricula())
                .enteOtorgador(certificacion.getEnteOtorgador())
                .fechaVencimiento(certificacion.getFechaVencimiento())
                .imagenBase64(imagenBase64)
                .tipoImagen(certificacion.getTipoImagen())
                .notasAdmin(certificacion.getNotasAdmin())
                .estadoVerificacion(certificacion.getEstadoVerificacion())
                .build();
    }

    public CertificacionEntity toEntity (CertificacionDTORequest dto){
        if (dto == null){
            return null;
        }
        return CertificacionEntity.builder()
                .titulo(dto.getTitulo())
                .numMatricula(dto.getNumMatricula())
                .enteOtorgador(dto.getEnteOtorgador())
                .fechaVencimiento(dto.getFechaVencimiento())
                .build();
    }
}