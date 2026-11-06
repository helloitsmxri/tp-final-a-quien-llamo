package com.aquienllamo.aquienllamo.entidades.portfolio.mapper;

import com.aquienllamo.aquienllamo.entidades.especialidad.entity.EspecialidadEntity;
import com.aquienllamo.aquienllamo.entidades.portfolio.dto.response.PortfolioUsuarioDTOResponse;
import com.aquienllamo.aquienllamo.entidades.portfolio.dto.request.PortfolioUsuarioDTORequest;
import com.aquienllamo.aquienllamo.entidades.portfolio.dto.response.PortfolioAdminDTOResponse;
import com.aquienllamo.aquienllamo.entidades.portfolio.entity.PortfolioEntity;
import com.aquienllamo.aquienllamo.entidades.tecnico.entity.TecnicoEntity;
import org.springframework.stereotype.Component;
import java.util.Base64;

@Component
public class PortfolioMapper {

    public static PortfolioEntity toEntity(
            PortfolioUsuarioDTORequest usuarioDTORequest,
            TecnicoEntity tecnico,
            EspecialidadEntity especialidad
    ) {
        return PortfolioEntity.builder()
                .tecnico(tecnico)
                .especialidad(especialidad)
                .titulo(usuarioDTORequest.getTitulo())
                .notasAspirante(usuarioDTORequest.getNotasAspirante())
                .enlaceExterno(usuarioDTORequest.getEnlaceExterno())
                .tipoArchivo(usuarioDTORequest.getTipoArchivo())
                .archivoAdjunto(usuarioDTORequest.getArchivoAdjunto())
                .build();
    }

    //portafolio de admin
    public static PortfolioAdminDTOResponse toResponseAdmin (PortfolioEntity entity){
        return PortfolioAdminDTOResponse.builder()
                .uuid(entity.getUuid())
                .titulo(entity.getTitulo())
                .uuidTecnico(entity.getTecnico().getUuid())
                .nombreTecnico(entity.getTecnico().getUsuario().getNombre())
                .nombreEspecialidad(entity.getEspecialidad().getNombreEspecialidad())
                .notasAspirante(entity.getNotasAspirante())
                .enlaceExterno(entity.getEnlaceExterno())
                .tipoArchivo(entity.getTipoArchivo())
                .estadoVerificacion(entity.getEstadoVerificacion())
                .notasAdmin(entity.getNotasAdmin())
                .fechaEntrega(entity.getFechaEntrega())
                .build();
    }


    //portafolio de usuario
    public static PortfolioUsuarioDTOResponse toResponseUsuario (PortfolioEntity entity){
        return PortfolioUsuarioDTOResponse.builder()
                .uuid(entity.getUuid())
                .titulo(entity.getTitulo())
                .nombreEspecialidad(entity.getEspecialidad().getNombreEspecialidad())
                .notasAspirante(entity.getNotasAspirante())
                .enlaceExterno(entity.getEnlaceExterno())
                .tipoArchivo(entity.getTipoArchivo())
                .estadoVerificacion(entity.getEstadoVerificacion())
                .notasAdmin(entity.getNotasAdmin())
                .fechaEntrega(entity.getFechaEntrega())
                .archivoAdjunto(
                        entity.getArchivoAdjunto() != null
                                ? Base64.getEncoder().encodeToString(entity.getArchivoAdjunto())
                                : null
                )
                .build();
    }

}