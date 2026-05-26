package com.aquienllamo.aquienllamo.model.services;
import com.aquienllamo.aquienllamo.model.dtos.Response.RubroDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.RubroEntity;
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

    private final RubroMapper mapper;
    private final RubroRepository rubroRepository;

    //enlistar los rubros
    public List<RubroDTOResponse> getAllRubros(){
        return rubroRepository.findAll()
                .stream()
                .map(mapper::toResponseRubro)
                .toList();
    }

    //buscar rubro por uuid
    public RubroDTOResponse getRubroByUuid(String uuid){
        RubroEntity rubro = rubroRepository.findByUuid(uuid)
                .orElseThrow();
        return mapper.toResponseRubro(rubro);
    }

    //buscar rubro por nombre
    public RubroDTOResponse getRubroByNombre(String nombre){
        RubroEntity rubro = rubroRepository.findByNombreRubro(nombre)
                .orElseThrow();
        return mapper.toResponseRubro(rubro);
    }

    //buscar por coincidencia o que contenga
    public List<RubroDTOResponse> findRubroContaining(String nombre){
        return rubroRepository.findByNombreRubroContainingIgnoreCase(nombre)
                .stream()
                .map(mapper::toResponseRubro)
                .toList();
    }


}
