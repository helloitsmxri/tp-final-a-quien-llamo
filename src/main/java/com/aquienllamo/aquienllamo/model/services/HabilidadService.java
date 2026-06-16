package com.aquienllamo.aquienllamo.model.services;

import com.aquienllamo.aquienllamo.model.dtos.Request.HabilidadDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.HabilidadCreadaDTOResponse;
import com.aquienllamo.aquienllamo.model.dtos.Response.HabilidadDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.HabilidadEntity;
import com.aquienllamo.aquienllamo.model.exceptions.HabilidadAlreadyExistsEx;
import com.aquienllamo.aquienllamo.model.exceptions.HabilidadNotFoundEx;
import com.aquienllamo.aquienllamo.model.mappers.HabilidadMapper;
import com.aquienllamo.aquienllamo.model.repositories.HabilidadRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class HabilidadService {
    private final HabilidadRepository habilidadRepository;
    private final HabilidadMapper habilidadMapper;

    //crear habilidad
    public HabilidadCreadaDTOResponse crearHabilidad(HabilidadDTORequest habilidad){
        if (habilidadRepository.existsByNombreHabilidad(habilidad.getNombreHabilidad())){
            throw new HabilidadAlreadyExistsEx("Ya existe una habilidad con ese nombre.");
        }
        HabilidadEntity nueva=habilidadMapper.toEntity(habilidad);
        habilidadRepository.save(nueva);
        return habilidadMapper.toResponseCreada(nueva);
    }

    //listar habilidades
    public List<HabilidadDTOResponse> listarHabilidades(){
        return habilidadRepository.findAll()
                .stream()
                .map(habilidadMapper::toResponse)
                .toList();
    }

    //actualizar
    public HabilidadDTOResponse actualizarHabilidad(String uuid, HabilidadDTORequest habilidad){
        HabilidadEntity nueva=habilidadRepository.findByUuid(uuid)
                .orElseThrow(()-> new HabilidadNotFoundEx("la habilidad con ese uuid no se encontro."));
        if (habilidadRepository.existsByNombreHabilidad(habilidad.getNombreHabilidad())){
            throw new HabilidadNotFoundEx("Ya existe una habilidad con ese nombre.");
        }
        nueva.setNombreHabilidad(habilidad.getNombreHabilidad());
        return habilidadMapper.toResponse(habilidadRepository.save(nueva));
    }

    //eliminar
    public void eliminarHabilidad(String uuid){
        HabilidadEntity habilidad=habilidadRepository.findByUuid(uuid)
                .orElseThrow(()-> new HabilidadNotFoundEx("la habilidad con ese uuid no existe"));
        habilidadRepository.delete(habilidad);
    }

}
