package com.aquienllamo.aquienllamo.model.services;
import com.aquienllamo.aquienllamo.model.dtos.Request.RubroDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.RubroDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.RubroEntity;
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

    private final RubroMapper mapper;
    private final RubroRepository rubroRepository;
    private final EspecialidadRepository especialidadRepository;

    //crear un rubro (esto le va a servir a admin en un futuro que quiera agregar rubros)
    public RubroDTOResponse crearRubro(RubroDTORequest request){
        if(rubroRepository.existsByNombreRubro(request.getNombreRubro())){
            throw new RubroAlreadyExistsEx("ERROR: El rubro "+request.getNombreRubro()+" ya existe.");
        }
        RubroEntity rubro = mapper.toEntity(request);
        return mapper.toResponseRubro(rubroRepository.save(rubro));
    }

    //modificar rubro (solo lo puede hacer el admin)
    public RubroDTOResponse modificarRubro(String uuid,RubroDTORequest request){
        RubroEntity rubro = rubroRepository.findByUuid(uuid)
                .orElseThrow(()-> new RubroNotFoundEx("ERROR: El rubro ingresado no existe."));
        rubro.setNombreRubro(request.getNombreRubro());
        rubro.setEspecialidades(
                request.getUuidEspecialidad()
                        .stream()
                        .map(especialidad -> especialidadRepository.findByUuid(especialidad)
                                .orElseThrow(()-> new EspecialidadNotFoundEx("ERROR: La especialidad " + especialidad + " no existe.")))
                        .toList()
        );
        return mapper.toResponseRubro(rubroRepository.save(rubro));
    }

    //eliminar un rubro
    public void eliminarRubro(String uuid){
        RubroEntity rubro = rubroRepository.findByUuid(uuid)
                .orElseThrow(()-> new RubroNotFoundEx("ERROR: El rubro ingresado no existe."));
        rubroRepository.delete(rubro);
    }

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
                .orElseThrow(()-> new RubroNotFoundEx("ERROR: El rubro ingresado no existe."));
        return mapper.toResponseRubro(rubro);
    }

    //buscar rubro por nombre
    public RubroDTOResponse getRubroByNombre(String nombre){
        RubroEntity rubro = rubroRepository.findByNombreRubro(nombre)
                .orElseThrow(()-> new RubroNotFoundEx("ERROR: El rubro "+nombre+" no existe."));
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
