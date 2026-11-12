package com.aquienllamo.aquienllamo.entidades.denuncia.mapper;

import com.aquienllamo.aquienllamo.entidades.denuncia.dto.request.DenunciaDTORequest;
import com.aquienllamo.aquienllamo.entidades.denuncia.dto.response.DenunciaDTOResponse;
import com.aquienllamo.aquienllamo.entidades.denuncia.entity.DenunciaEntity;
import org.springframework.stereotype.Component;

@Component
public class DenunciaMapper {
    public DenunciaDTOResponse toResponse(DenunciaEntity denuncia){

        return DenunciaDTOResponse.builder()
                .uuid(denuncia.getUuid())
                .uuidAdministrador(denuncia.getAdministrador() != null
                                    ? denuncia.getAdministrador().getUuid()
                                    : null
                )
                .nombreAdministrador(denuncia.getAdministrador() != null
                                    ? denuncia.getAdministrador().getNombre()
                                    : null)
                .uuidDenunciante(denuncia.getDenunciante().getUuid())
                .nombreDenunciante(denuncia.getDenunciante().getNombre())
                .apellidoDenunciante(denuncia.getDenunciante().getApellido())
                .emailDenunciante(denuncia.getDenunciante().getEmail())

                .uuidDenunciado(denuncia.getDenunciado().getUuid())
                .nombreDenunciado(denuncia.getDenunciado().getNombre())
                .apellidoDenunciado(denuncia.getDenunciado().getApellido())
                .emailDenunciado(denuncia.getDenunciado().getEmail())

                .estadoDenuncia(denuncia.getEstadoDenuncia())
                .motivoDenuncia(denuncia.getMotivoDenuncia())
                .notaDelAdmin(denuncia.getNotaDelAdmin())
                .fechaDenuncia(denuncia.getFechaDenuncia())

                .tieneFoto(
                        denuncia.getFoto() != null
                        && denuncia.getFoto().length > 0
                )
                .tipoFoto(denuncia.getTipoFoto())
                .build();
    }

    public DenunciaEntity toEntity(DenunciaDTORequest dto){

        return DenunciaEntity.builder()
                .motivoDenuncia(dto.getMotivoDenuncia())
                .build();
    }
}
