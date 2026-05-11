package com.aquienllamo.aquienllamo.model.services;

import com.aquienllamo.aquienllamo.model.dtos.Request.UbicacionDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.UbicacionDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.UbicacionEntity;
import com.aquienllamo.aquienllamo.model.entities.UsuarioEntity;
import com.aquienllamo.aquienllamo.model.exceptions.UbicacionNotFoundEx;
import com.aquienllamo.aquienllamo.model.exceptions.UserNotFoundEx;
import com.aquienllamo.aquienllamo.model.mappers.UbicacionMapper;
import com.aquienllamo.aquienllamo.model.repositories.UbicacionRepository;
import com.aquienllamo.aquienllamo.model.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UbicacionService {

    private final UbicacionRepository ubicacionRepository;
    private final UsuarioRepository usuarioRepository;
    private final UbicacionMapper  ubicacionMapper;

    //crear ubicacion
    public UbicacionDTOResponse crearUbicacion(UbicacionDTORequest dto, String uuidUsuario) {
        UsuarioEntity usuario = usuarioRepository.findByUuid(uuidUsuario)
                .orElseThrow(UserNotFoundEx::new);

        UbicacionEntity ubicacion = ubicacionMapper.toEntity(dto);
        ubicacion.setUsuario(usuario);

        return ubicacionMapper.toResponse(ubicacionRepository.save(ubicacion));

    }
    // obtener todas las ubicaciones de un usuario
    public List<UbicacionDTOResponse> getUbicacionesByUsuario(String uuidUsuario) {
        UsuarioEntity usuario = usuarioRepository.findByUuid(uuidUsuario)
                .orElseThrow(UserNotFoundEx::new);

        return ubicacionRepository.findByUsuario_IdUsuario(usuario.getIdUsuario())                .stream()
                .map(ubicacionMapper::toResponse)
                .toList();
    }

    // obtener ubicación por uuid
    public UbicacionDTOResponse getUbicacionByUuid(String uuid) {
        return ubicacionMapper.toResponse(
                ubicacionRepository.findByUuid(uuid)
                        .orElseThrow(UbicacionNotFoundEx::new)
        );
    }

    // actualizar ubicación
    public UbicacionDTOResponse updateUbicacion(String uuid, UbicacionDTORequest dto) {
        UbicacionEntity ubicacion = ubicacionRepository.findByUuid(uuid)
                .orElseThrow(UbicacionNotFoundEx::new);

        ubicacion.setCodigoPostal(dto.getCodigoPostal());
        ubicacion.setProvincia(dto.getProvincia());
        ubicacion.setCiudad(dto.getCiudad());
        ubicacion.setCalle(dto.getCalle());
        ubicacion.setNumero(dto.getNumero());
        ubicacion.setPiso(dto.getPiso());
        ubicacion.setNumeroPiso(dto.getNumeroPiso());

        return ubicacionMapper.toResponse(ubicacionRepository.save(ubicacion));
    }

    // eliminar ubicación
    public void deleteUbicacion(String uuid) {
        UbicacionEntity ubicacion = ubicacionRepository.findByUuid(uuid)
                .orElseThrow(UbicacionNotFoundEx::new);
        ubicacionRepository.delete(ubicacion);
    }
}

