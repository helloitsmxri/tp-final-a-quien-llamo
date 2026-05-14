package com.aquienllamo.aquienllamo.model.services;

import com.aquienllamo.aquienllamo.model.dtos.Request.CaracteristicaDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.CaracteristicaDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.CaracteristicaEntity;
import com.aquienllamo.aquienllamo.model.mappers.CaracteristicaMapper;
import com.aquienllamo.aquienllamo.model.mappers.UsuarioMapper;
import com.aquienllamo.aquienllamo.model.repositories.CaracteristicaRepository;
import com.aquienllamo.aquienllamo.model.repositories.TecnicoRepository;
import com.aquienllamo.aquienllamo.model.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class CaracteristicaService {

    private final CaracteristicaRepository caracteristicaRepository;
    private final CaracteristicaMapper caracteristicaMapper;

    // listar todas las características:
    public List<CaracteristicaEntity> findAll(){
        return List.copyOf(caracteristicaRepository.findAll());
    }

    // encontrar una característica...
    public CaracteristicaEntity findByUuid(String uuid){
        return caracteristicaRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("No se encontró la característica."));
    }

    // crear una característica nueva:
    public CaracteristicaDTOResponse crearNuevaCaracteristica(CaracteristicaDTORequest dtoRequest){
        if (dtoRequest.getValorAdjetivo() == null || dtoRequest.getValorAdjetivo().isEmpty()){
            throw new RuntimeException("No se puede enviar un campo vacío.");
        }

        // comparar si todos los nombres son iguales...

        CaracteristicaEntity caracteristica = caracteristicaMapper.toEntity(dtoRequest);

        // guardo entidad y retorno de una response.
        return caracteristicaMapper.toResponse(caracteristicaRepository.save(caracteristica));

    }

    // eliminar una característica:
    public void eliminarCaracteristica(String uuid){
//        if (!caracteristicaRepository.existsByUuid(uuid)){
//            throw new RuntimeException("No se encontró dicha característica.");
//        }

        CaracteristicaEntity c = caracteristicaRepository.findByUuid(uuid)
                        .orElseThrow(() -> new RuntimeException("No se encontró")); // esto último no debería pasar
                                                                                    // xq yo ya verifiqué su existencia en el if
        caracteristicaRepository.delete(c);
    }

    // modificar una característica:
    public CaracteristicaDTOResponse modificar(String uuid, CaracteristicaDTORequest dtoRequest){
        CaracteristicaEntity c = caracteristicaRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("No se halló la característica."));

        String nuevoAdjetivo = dtoRequest.getValorAdjetivo();

        if (c.getValorAdjetivo().equals(nuevoAdjetivo)){
            throw new RuntimeException("Estás intentando ingresar el mismo nombre para la característica.");
        }

        c.setValorAdjetivo(nuevoAdjetivo);

        return caracteristicaMapper.toResponse(caracteristicaRepository.save(c));
    }

}
