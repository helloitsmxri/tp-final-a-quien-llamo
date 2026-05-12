package com.aquienllamo.aquienllamo.model.services;

import com.aquienllamo.aquienllamo.model.dtos.Request.RubroDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.RubroDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.RubroEntity;
import com.aquienllamo.aquienllamo.model.exceptions.RubroAlreadyExistsEx;
import com.aquienllamo.aquienllamo.model.mappers.RubroMapper;
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

    //crear un rubro
    public RubroDTOResponse crearRurbo(RubroDTORequest dto){

        if(rubroRepository.existsByNameField(dto.getNombreRubro())){
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
        RubroEntity rubro = rubroRepository.findByUUIDField(uuid)
                .orElseThrow();
        return rubroMapper.toResponseRubro(rubro);
    }

    //buscar rubro por nombre
    public RubroDTOResponse getRubroByNombre(String nombre){
        RubroEntity rubro = rubroRepository.findByFieldName(nombre)
                .orElseThrow();
        return rubroMapper.toResponseRubro(rubro);
    }

    //buscar por coincidencia o que contenga
    public List<RubroDTOResponse> findRubroContaining(String nombre){
        return rubroRepository.findByNameFieldContainingIgnoreCase(nombre)
                .stream()
                .map(rubroMapper::toResponseRubro)
                .toList();
    }

}
