package com.aquienllamo.aquienllamo.model.services;

import com.aquienllamo.aquienllamo.model.dtos.Request.HabilidadDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.HabilidadDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.HabilidadEntity;
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
    public HabilidadDTOResponse crearHabilidad(HabilidadDTORequest habilidad){
        HabilidadEntity nueva=habilidadMapper.toEntity(habilidad);
        return habilidadMapper.toResponse(habilidadRepository.save(nueva));
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
