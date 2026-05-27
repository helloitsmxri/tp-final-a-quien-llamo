package com.aquienllamo.aquienllamo.model.mappers;

import com.aquienllamo.aquienllamo.model.dtos.Request.PortfolioUsuarioDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.PortfolioAdminDTOResponse;
import com.aquienllamo.aquienllamo.model.dtos.Response.PortfolioUsuarioDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.EspecialidadEntity;
import com.aquienllamo.aquienllamo.model.entities.PortfolioEntity;
import org.springframework.stereotype.Component;

@Component
public class PortfolioMapper {

    public static PortfolioEntity toEntity (PortfolioUsuarioDTORequest usuarioDTORequest, EspecialidadEntity especialidad){
        return PortfolioEntity.builder()
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
                .tecnico(entity.getTecnico().getIdTecnico())
                .especialidad(entity.getEspecialidad().getIdEspecialidad())
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
