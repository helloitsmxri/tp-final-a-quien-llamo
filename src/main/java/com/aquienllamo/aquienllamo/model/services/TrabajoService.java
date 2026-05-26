package com.aquienllamo.aquienllamo.model.services;

import com.aquienllamo.aquienllamo.model.Enum.EstadoTrabajo;
import com.aquienllamo.aquienllamo.model.dtos.Request.TrabajoDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.TrabajoDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.TrabajoEntity;
import com.aquienllamo.aquienllamo.model.exceptions.TrabajoAlreadyExistsEx;
import com.aquienllamo.aquienllamo.model.exceptions.TrabajoNotFoundEx;
import com.aquienllamo.aquienllamo.model.mappers.TrabajoMapper;
import com.aquienllamo.aquienllamo.model.repositories.TrabajoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TrabajoService {

    private final TrabajoRepository trabajoRepository;

    //crear un trabajo
    public TrabajoDTOResponse crearTrabajo (TrabajoDTORequest trabajoDTORequest) {

        TrabajoEntity trabajoEntity = trabajoRepository.findByUuid(trabajoDTORequest.getUuid())
                .orElseThrow(()-> new TrabajoAlreadyExistsEx("ERROR: El trabajo ingresado ya existe."));

        return TrabajoMapper.toResponse(trabajoRepository.save(trabajoEntity));

    }

    //listar todos los trabajos
    public List<TrabajoDTOResponse> listarTrabajos(){
        return trabajoRepository.findAll()
                .stream()
                .map(TrabajoMapper::toResponse)
                .toList();
    }

    //listar trabajo por estado de trabajo
    public List<TrabajoDTOResponse> listarTrabajoPorEstadoTrabajo(EstadoTrabajo estadoTrabajo){
        return trabajoRepository.findAllByEstadoTrabajo(estadoTrabajo)
                .stream()
                .map(TrabajoMapper::toResponse)
                .toList();
    }
    

}
