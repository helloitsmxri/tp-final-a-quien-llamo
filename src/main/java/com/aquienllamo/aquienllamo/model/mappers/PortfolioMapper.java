package com.aquienllamo.aquienllamo.model.mappers;

import com.aquienllamo.aquienllamo.model.dtos.Request.PortfolioUsuarioDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.PortfolioAdminDTOResponse;
import com.aquienllamo.aquienllamo.model.dtos.Response.PortfolioUsuarioDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.EspecialidadEntity;
import com.aquienllamo.aquienllamo.model.entities.PortfolioEntity;
import com.aquienllamo.aquienllamo.model.entities.TecnicoEntity;
import org.springframework.stereotype.Component;

@Component
public class PortfolioMapper {

    public static PortfolioEntity toEntity (PortfolioUsuarioDTORequest usuarioDTORequest, TecnicoEntity tecnico, EspecialidadEntity especialidad){
        return PortfolioEntity.builder()
                .tecnico(tecnico)
                .especialidad(especialidad)
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
                .uuidTecnico(entity.getTecnico().getUuid())
                .nombreTecnico(entity.getTecnico().getUsuario().getNombre())
                .nombreEspecialidad(entity.getEspecialidad().getNombreEspecialidad())
                .notasAspirante(entity.getNotasAspirante())
                .enlaceExterno(entity.getEnlaceExterno())
                .tipoArchivo(entity.getTipoArchivo())
                .fechaEntrega(entity.getFechaEntrega())
                .build();
    }


    //portafolio de usuario
    public static PortfolioUsuarioDTOResponse toResponseUsuario (PortfolioEntity entity){
        return PortfolioUsuarioDTOResponse.builder()
                .estadoVerificacion(entity.getEstadoVerificacion())
                .notasAdmin(entity.getNotasAdmin())
                .build();
    }
    
}
