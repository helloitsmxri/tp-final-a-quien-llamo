package com.aquienllamo.aquienllamo.model.services;

import com.aquienllamo.aquienllamo.model.Enum.EstadoPresupuestoE;
import com.aquienllamo.aquienllamo.model.Enum.EstadoTrabajo;
import com.aquienllamo.aquienllamo.model.dtos.Request.TrabajoDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.TrabajoDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.PresupuestoEntity;
import com.aquienllamo.aquienllamo.model.entities.TrabajoEntity;
import com.aquienllamo.aquienllamo.model.entities.UsuarioEntity;
import com.aquienllamo.aquienllamo.model.exceptions.PresupuestoNotFoundEx;
import com.aquienllamo.aquienllamo.model.exceptions.TrabajoAlreadyExistsEx;
import com.aquienllamo.aquienllamo.model.exceptions.TrabajoNotFoundEx;
import com.aquienllamo.aquienllamo.model.mappers.TrabajoMapper;
import com.aquienllamo.aquienllamo.model.repositories.PresupuestoRepository;
import com.aquienllamo.aquienllamo.model.repositories.TrabajoRepository;
import com.aquienllamo.aquienllamo.model.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TrabajoService {

    private final TrabajoRepository trabajoRepository;
    private final PresupuestoRepository presupuestoRepository;
    private final UsuarioRepository  usuarioRepository;

    //crear un trabajo
    public TrabajoDTOResponse crearTrabajo (TrabajoDTORequest trabajoDTORequest) {

        PresupuestoEntity presupuesto = presupuestoRepository.findByUuid(trabajoDTORequest.getUuidPresupuesto())
                .orElseThrow(()-> new PresupuestoNotFoundEx("ERROR: El presupuesto ingresado no existe."));

        if(!presupuesto.getEstado().equals(EstadoPresupuestoE.Aceptado)){
            throw new PresupuestoNotFoundEx("ERROR: Solo se pueden crear trabajos con presupuestos aceptados.");
        }

        if(trabajoRepository.existsByPresupuestoUuid(trabajoDTORequest.getUuidPresupuesto())){
            throw new TrabajoAlreadyExistsEx("ERROR: El trabajo ya existe.");
        }

        TrabajoEntity trabajo = TrabajoMapper.toEntity(trabajoDTORequest,presupuesto);
        trabajo.setEstadoTrabajo(EstadoTrabajo.Pendiente);

        return TrabajoMapper.toResponse(trabajoRepository.save(trabajo));

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

    //cambiar estado de trabajo por el tecnico
    public TrabajoDTOResponse cambiarEstadoTrabajo(String uuidTrabajo, EstadoTrabajo nuevoEstado) {
        TrabajoEntity trabajo = trabajoRepository.findByUuid(uuidTrabajo)
                .orElseThrow(() -> new TrabajoNotFoundEx("ERROR: El trabajo ingresado no existe"));
        trabajo.setEstadoTrabajo(nuevoEstado);
        return TrabajoMapper.toResponse(trabajoRepository.save(trabajo));
    }
    
    //enlistar a todos los trabajos por cliente
    public List<TrabajoDTOResponse> listarTrabajosPorCliente (String uuidCliente){
        return trabajoRepository.findAllByUuidUsuario(uuidCliente)
                .stream()
                .map(TrabajoMapper::toResponse)
                .toList();
    }

    //enlistar a todos los trabajos por tecnico
    public List<TrabajoDTOResponse> listarTrabajosPorTecnico(String uuidTecnico){
        return trabajoRepository.findAllByUuidTecnico(uuidTecnico)
                .stream()
                .map(TrabajoMapper::toResponse)
                .toList();
    }

    //un cliente solo puede ver sus propios
    // trabajos, un técnico solo puede ver los suyos, y
    // el administrador puede ver todos
    public boolean perteneceAlUsuario(String uuidUsuario, String email){
        return usuarioRepository.findByUuid(uuidUsuario)
                .map(usuario -> usuario.getEmail().equals(email))
                .orElse(false);
    }


}
