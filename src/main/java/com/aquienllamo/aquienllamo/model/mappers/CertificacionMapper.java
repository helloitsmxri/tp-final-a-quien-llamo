package com.aquienllamo.aquienllamo.model.mappers;

import com.aquienllamo.aquienllamo.model.dtos.Request.CertificacionDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.CertificacionDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.CertificacionEntity;
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
                .nombreTecnico(certificacion.getTecnico().getUsuario().getNombre())
                .apellidoTecnico(certificacion.getTecnico().getUsuario().getApellido())
                .numMatricula(certificacion.getNumMatricula())
                .enteOtorgador(certificacion.getEnteOtorgador())
                .fechaVencimiento(certificacion.getFechaVencimiento())
                .imagenBase64(imagenBase64)
                .tipoImagen(certificacion.getTipoImagen())
                .build();
    }

    public CertificacionEntity toEntity (CertificacionDTORequest dto){
        if (dto == null){
            return null;
        }
        return CertificacionEntity.builder()
                .numMatricula(dto.getNumMatricula())
                .enteOtorgador(dto.getEnteOtorgador())
                .fechaVencimiento(dto.getFechaVencimiento())
                .build();
    }
}
