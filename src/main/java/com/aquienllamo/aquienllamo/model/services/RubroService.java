package com.aquienllamo.aquienllamo.model.services;

import com.aquienllamo.aquienllamo.model.dtos.Request.RubroDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.RubroDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.EspecialidadEntity;
import com.aquienllamo.aquienllamo.model.entities.RubroEntity;
import com.aquienllamo.aquienllamo.model.exceptions.RubroAlreadyExistsEx;
import com.aquienllamo.aquienllamo.model.mappers.RubroMapper;
import com.aquienllamo.aquienllamo.model.repositories.EspecialidadRepository;
import com.aquienllamo.aquienllamo.model.repositories.RubroRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RubroService {

    private final RubroRepository rubroRepository;
    private final RubroMapper rubroMapper;
    private final EspecialidadRepository especialidadRepository;

    //crear un rubro
    public RubroDTOResponse crearRurbo(RubroDTORequest dto){

        if(rubroRepository.existsByNombreRubro(dto.getNombreRubro())){
            throw new RubroAlreadyExistsEx("El rubro ya existe.");
        }

        RubroEntity rubro = rubroMapper.toEntity(dto);

        //pasa el entity a un response a traves del mapper
        return rubroMapper.toResponseRubro(rubroRepository.save(rubro));
    }

    //enlistar los rubros
    public List<RubroDTOResponse> getAllRubros(){
        return rubroRepository.findAll()
                .stream()
                .map(rubroMapper::toResponseRubro)
                .toList();
    }

    //buscar rubro por uuid
    public RubroDTOResponse getRubroByUuid(String uuid){
        RubroEntity rubro = rubroRepository.findByUuid(uuid)
                .orElseThrow();
        return rubroMapper.toResponseRubro(rubro);
    }

    //buscar rubro por nombre
    public RubroDTOResponse getRubroByNombre(String nombre){
        RubroEntity rubro = rubroRepository.findByNombreRubro(nombre)
                .orElseThrow();
        return rubroMapper.toResponseRubro(rubro);
    }

    //buscar por coincidencia o que contenga
    public List<RubroDTOResponse> findRubroContaining(String nombre){
        return rubroRepository.findByNombreRubroContainingIgnoreCase(nombre)
                .stream()
                .map(rubroMapper::toResponseRubro)
                .toList();
    }

    //actualizar rubro
    public RubroDTOResponse updateRubroUuid(String uuid, RubroDTORequest dto){

        //busco el rubro
        RubroEntity rubroAux = rubroRepository
                .findByUuid(uuid)
                .orElseThrow();

        //actualizar nombre del rubro
        rubroAux.setNombreRubro(dto.getNombreRubro());

        //buscar las especialidades
        List<EspecialidadEntity> especialidadAux = dto.getUuidEspecialidad()
                .stream()
                .map(uuidEspecialidad -> especialidadRepository
                        .findByUuid(uuidEspecialidad)
                        .orElseThrow())
                .toList();

        //actualizar especialidades
        rubroAux.setEspecialidades(especialidadAux);

        //guardar los cambios hechos
        RubroEntity rubroActualizado = rubroRepository.save(rubroAux);

        //devuelve un dto
        return rubroMapper.toResponseRubro(rubroActualizado);
    }

    //eliminar un rubro por uuid
    public void deleteRubroByUuid(String uuid){
        RubroEntity rubro = rubroRepository
                .findByUuid(uuid)
                .orElseThrow();
        rubroRepository.delete(rubro);
    }

}
