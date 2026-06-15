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

        return DenunciaDTOResponse.builder()
                .uuid(denuncia.getUuid())
                .nombreAdministrador(
                        denuncia.getAdministrador() != null
                                ? denuncia.getAdministrador().getNombreUsuario()
                                : null
                )
                .nombreDenunciante(denuncia.getDenunciante().getNombre())
                .apellidoDenunciante(denuncia.getDenunciante().getApellido())
                .emailDenunciante(denuncia.getDenunciante().getEmail())
                .nombreDenunciado(denuncia.getDenunciado().getNombre())
                .apellidoDenunciado(denuncia.getDenunciado().getApellido())
                .emailDenunciado(denuncia.getDenunciado().getEmail())
                .estadoDenuncia(denuncia.getEstadoDenuncia())
                .motivoDenuncia(denuncia.getMotivoDenuncia())
                .notaDelAdmin(denuncia.getNotaDelAdmin())
                .fechaDenuncia(denuncia.getFechaDenuncia())
                .build();
    }

    public DenunciaEntity toEntity(DenunciaDTORequest dto){

        return DenunciaEntity.builder()
                .motivoDenuncia(dto.getMotivoDenuncia())
                .build();
    }
}
