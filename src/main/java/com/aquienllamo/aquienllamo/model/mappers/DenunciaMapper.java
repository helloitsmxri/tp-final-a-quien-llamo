package com.aquienllamo.aquienllamo.model.mappers;

import com.aquienllamo.aquienllamo.model.dtos.Request.DenunciaDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.DenunciaDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.DenunciaEntity;
import org.springframework.stereotype.Component;

import javax.xml.transform.sax.SAXResult;
import java.util.Base64;

@Component
public class DenunciaMapper {
    public DenunciaDTOResponse toResponse(DenunciaEntity denuncia){
        if (denuncia == null){
            return null;
        }
        return DenunciaDTOResponse.builder()
                .uuid(denuncia.getUuid())
                .administrador(denuncia.getAdministrador())
                .nombreDenunciante(denuncia.getNombreDenunciante())
                .apellidoDenunciante(denuncia.getApellidoDenunciante())
                .nombreDenunciado(denuncia.getNombreDenunciado())
                .apellidoDenunciado(denuncia.getApellidoDenunciado())
                .estadoDenuncia(denuncia.getEstadoDenuncia())
                .motivoDenuncia(denuncia.getMotivoDenuncia())
                .fechaDenuncia(denuncia.getFechaDenuncia())
                .build();
    }

    public DenunciaEntity toEntity(DenunciaDTORequest dto){
        if (dto == null){
            return null;
        }
        return DenunciaEntity.builder()
                .motivoDenuncia(dto.getMotivoDenuncia())
                .build();
    }
}
