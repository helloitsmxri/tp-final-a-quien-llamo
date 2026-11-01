package com.aquienllamo.aquienllamo.entidades.trabajo.service;

import com.aquienllamo.aquienllamo.APIs.GoogleGmail.EmailService;
import com.aquienllamo.aquienllamo.entidades.presupuesto.enums.EstadoPresupuestoE;
import com.aquienllamo.aquienllamo.entidades.presupuesto.entity.PresupuestoEntity;
import com.aquienllamo.aquienllamo.entidades.presupuesto.exceptions.PresupuestoNotFoundEx;
import com.aquienllamo.aquienllamo.entidades.presupuesto.repository.PresupuestoRepository;
import com.aquienllamo.aquienllamo.entidades.trabajo.dto.request.TrabajoDTORequest;
import com.aquienllamo.aquienllamo.entidades.trabajo.dto.response.TrabajoDTOResponse;
import com.aquienllamo.aquienllamo.entidades.trabajo.entity.TrabajoEntity;
import com.aquienllamo.aquienllamo.entidades.trabajo.enums.EstadoTrabajo;
import com.aquienllamo.aquienllamo.entidades.trabajo.exceptions.TrabajoAlreadyExistsEx;
import com.aquienllamo.aquienllamo.entidades.trabajo.exceptions.TrabajoNotFoundEx;
import com.aquienllamo.aquienllamo.entidades.trabajo.mapper.TrabajoMapper;
import com.aquienllamo.aquienllamo.entidades.trabajo.repository.TrabajoRepository;
import com.aquienllamo.aquienllamo.entidades.usuario.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TrabajoService {

    private final TrabajoRepository trabajoRepository;
    private final PresupuestoRepository presupuestoRepository;
    private final UsuarioRepository  usuarioRepository;
    private final EmailService emailService;

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

        trabajoRepository.save(trabajo);
        emailService.enviarTrabajoCreado(presupuesto.getUsuario().getEmail(), trabajo.getDescripcionTrabajo());
        return TrabajoMapper.toResponse(trabajo);

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

    //obtener un trabajo por el uuid
    public TrabajoDTOResponse obtenerTrabajoPorUuid (String uuidTrabajo){
        TrabajoEntity trabajo = trabajoRepository.findByUuid(uuidTrabajo)
                .orElseThrow(()-> new TrabajoNotFoundEx("ERROR: El trabajo ingresado no existe"));
        return TrabajoMapper.toResponse(trabajo);
    }

}
