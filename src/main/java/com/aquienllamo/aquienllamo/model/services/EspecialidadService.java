package com.aquienllamo.aquienllamo.model.services;

import com.aquienllamo.aquienllamo.model.Enum.TipoValidacion;
import com.aquienllamo.aquienllamo.model.dtos.Request.EspecialidadDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.EspecialidadDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.EspecialidadEntity;
import com.aquienllamo.aquienllamo.model.exceptions.EspecialidadAlreadyExistsEx;
import com.aquienllamo.aquienllamo.model.exceptions.EspecialidadNotFoundEx;
import com.aquienllamo.aquienllamo.model.mappers.EspecialidadMapper;
import com.aquienllamo.aquienllamo.model.repositories.EspecialidadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EspecialidadService {
    private final EspecialidadRepository especialidadRepository;
    private final EspecialidadMapper  especialidadMapper;

    //crear especialidad
    public EspecialidadDTOResponse createEspecialidad(EspecialidadDTORequest dto){
        if (especialidadRepository.existsByNombreEspecialidad(dto.getNombreEspecialidad())){
           throw new EspecialidadAlreadyExistsEx("Ya existe una especialidad con ese nombre.");
        }
        EspecialidadEntity especialidad=especialidadMapper.toEntity(dto);
        return especialidadMapper.toResponse(especialidadRepository.save(especialidad));
    }

    //listar especialidades
    public List<EspecialidadDTOResponse> getAllEspecialidades(){
        return especialidadRepository.findAll()
                .stream()
                .map(especialidadMapper::toResponse)
                .toList();
    }

    //buscar por uuid
    public EspecialidadDTOResponse getEspecialidadByUuid(String uuid){
        return especialidadMapper.toResponse(
                especialidadRepository.findByUuid(uuid)
                        .orElseThrow(EspecialidadNotFoundEx::new)
        );
    }

    //buscar por nombre
    public List<EspecialidadDTOResponse> getEspecialidadesByNombre(String nombre){
        return especialidadRepository.findByNombreEspecialidadContainingIgnoreCase(nombre)
                .stream()
                .map(especialidadMapper::toResponse)
                .toList();
    }

    //filtrar por tipo de validacion
    public List<EspecialidadDTOResponse> getEspecialidadesByTipoValidacion(TipoValidacion tipoValidacion){
        return especialidadRepository.findByTipoValidacion(tipoValidacion)
                .stream()
                .map(especialidadMapper::toResponse)
                .toList();
    }

    //actualizar
    public EspecialidadDTOResponse updateEspecialidad(String uuid, EspecialidadDTORequest dto){
        EspecialidadEntity especialidad = especialidadRepository.findByUuid(uuid)
                .orElseThrow(EspecialidadNotFoundEx::new);
        especialidad.setNombreEspecialidad(dto.getNombreEspecialidad());
        especialidad.setTipoValidacion(dto.getTipoValidacion());
        return especialidadMapper.toResponse(especialidadRepository.save(especialidad));
    }

    //eliminar
    public void deleteEspecialidad(String uuid){
        EspecialidadEntity especialidad=especialidadRepository.findByUuid(uuid)
                .orElseThrow(EspecialidadNotFoundEx::new);
        especialidadRepository.delete(especialidad);
    }
}
