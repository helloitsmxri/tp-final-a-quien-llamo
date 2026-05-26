package com.aquienllamo.aquienllamo.model.services;

import com.aquienllamo.aquienllamo.model.dtos.Request.RubroDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.RubroDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.EspecialidadEntity;
import com.aquienllamo.aquienllamo.model.entities.RubroEntity;
import com.aquienllamo.aquienllamo.model.exceptions.EspecialidadAlreadyExistsEx;
import com.aquienllamo.aquienllamo.model.exceptions.EspecialidadNotFoundEx;
import com.aquienllamo.aquienllamo.model.exceptions.RubroAlreadyExistsEx;
import com.aquienllamo.aquienllamo.model.exceptions.RubroNotFoundEx;
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
    private final EspecialidadRepository especialidadRepository;

    //crear un rubro
    public RubroDTOResponse crearRurbo(RubroDTORequest dto){
        RubroEntity rubro = rubroRepository.findByNombreRubro(dto.getNombreRubro())
                .orElseThrow(()-> new RubroAlreadyExistsEx("ERROR: El rubro ingresado ya existe."));
        return RubroMapper.toResponseRubro(rubro);
    }

    //enlistar los rubros
    public List<RubroDTOResponse> getAllRubros(){
        return rubroRepository.findAll()
                .stream()
                .map(RubroMapper::toResponseRubro)
                .toList();
    }

    //buscar rubro por uuid
    public RubroDTOResponse getRubroByUuid(String uuid){
        RubroEntity rubro = rubroRepository.findByUuid(uuid)
                .orElseThrow();
        return RubroMapper.toResponseRubro(rubro);
    }

    //buscar rubro por nombre
    public RubroDTOResponse getRubroByNombre(String nombre){
        RubroEntity rubro = rubroRepository.findByNombreRubro(nombre)
                .orElseThrow();
        return RubroMapper.toResponseRubro(rubro);
    }

    //buscar por coincidencia o que contenga
    public List<RubroDTOResponse> findRubroContaining(String nombre){
        return rubroRepository.findByNombreRubroContainingIgnoreCase(nombre)
                .stream()
                .map(RubroMapper::toResponseRubro)
                .toList();
    }

    //actualizar rubro
    public RubroDTOResponse updateRubroUuid(String uuid, RubroDTORequest dto){

        //busco el rubro
        RubroEntity rubroAux = rubroRepository
                .findByUuid(uuid)
                .orElseThrow(()-> new RubroNotFoundEx("ERROR: El rubro ingresado no existe."));

        //actualizar nombre del rubro
        rubroAux.setNombreRubro(dto.getNombreRubro());

        //buscar las especialidades
        List<EspecialidadEntity> especialidadAux = dto.getUuidEspecialidad()
                .stream()
                .map(uuidEspecialidad -> especialidadRepository
                        .findByUuid(uuidEspecialidad)
                        .orElseThrow(()-> new EspecialidadNotFoundEx("ERROR: La especialidad ingresada no existe.")))
                .toList();

        //actualizar especialidades
        rubroAux.setEspecialidades(especialidadAux);

        //guardar los cambios hechos
        rubroRepository.save(rubroAux);

        //devuelve un dto
        return RubroMapper.toResponseRubro(rubroAux);
    }

    //eliminar un rubro por uuid
    public RubroDTOResponse deleteRubroByUuid(String uuid){
        RubroEntity rubro = rubroRepository
                .findByUuid(uuid)
                .orElseThrow(()-> new RubroNotFoundEx("ERROR: El rubro ingresado no existe."));
        return RubroMapper.toResponseRubro(rubroRepository.delete(rubro));
    }

}
