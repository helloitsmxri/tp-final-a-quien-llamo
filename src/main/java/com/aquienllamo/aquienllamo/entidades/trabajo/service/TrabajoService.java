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
import com.aquienllamo.aquienllamo.entidades.trabajo.exceptions.TrabajoNotAcceptedEx;
import com.aquienllamo.aquienllamo.entidades.trabajo.exceptions.TrabajoNotFoundEx;
import com.aquienllamo.aquienllamo.entidades.trabajo.mapper.TrabajoMapper;
import com.aquienllamo.aquienllamo.entidades.trabajo.repository.TrabajoRepository;
import com.aquienllamo.aquienllamo.entidades.usuario.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
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

    //crear un trabajo (solo el técnico dueño del presupuesto)
    public TrabajoDTOResponse crearTrabajo (TrabajoDTORequest trabajoDTORequest, String emailTecnico) {

        PresupuestoEntity presupuesto = presupuestoRepository.findByUuid(trabajoDTORequest.getUuidPresupuesto())
                .orElseThrow(()-> new PresupuestoNotFoundEx("ERROR: El presupuesto ingresado no existe."));

        if (!presupuesto.getTecnico().getUsuario().getEmail().equals(emailTecnico)) {
            throw new AccessDeniedException("Solo el tecnico de este presupuesto puede crear el trabajo.");
        }

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

    //listar todos los trabajos (solo admin, lo controla el controller)
    public List<TrabajoDTOResponse> listarTrabajos(){
        return trabajoRepository.findAll()
                .stream()
                .map(TrabajoMapper::toResponse)
                .toList();
    }

    //listar trabajo por estado de trabajo (solo admin)
    public List<TrabajoDTOResponse> listarTrabajoPorEstadoTrabajo(EstadoTrabajo estadoTrabajo){
        return trabajoRepository.findAllByEstadoTrabajo(estadoTrabajo)
                .stream()
                .map(TrabajoMapper::toResponse)
                .toList();
    }

    // NUEVO: los trabajos de la persona logueada (como cliente o como técnico)
    public List<TrabajoDTOResponse> listarMisTrabajos(String email){
        return trabajoRepository.findAllByParticipante(email)
                .stream()
                .map(TrabajoMapper::toResponse)
                .toList();
    }

    //cambiar estado de trabajo: solo el técnico de ese trabajo (o un admin)
    public TrabajoDTOResponse cambiarEstadoTrabajo(String uuidTrabajo, EstadoTrabajo nuevoEstado,
                                                   String email, boolean esAdmin) {
        TrabajoEntity trabajo = trabajoRepository.findByUuid(uuidTrabajo)
                .orElseThrow(() -> new TrabajoNotFoundEx("ERROR: El trabajo ingresado no existe"));

        if (!esAdmin && !esElTecnico(trabajo, email)) {
            throw new AccessDeniedException("Solo el tecnico de este trabajo puede cambiar su estado.");
        }

        validarTransicion(trabajo.getEstadoTrabajo(), nuevoEstado);

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

    //obtener un trabajo por el uuid: solo el cliente, el técnico o un admin
    public TrabajoDTOResponse obtenerTrabajoPorUuid (String uuidTrabajo, String email, boolean esAdmin){
        TrabajoEntity trabajo = trabajoRepository.findByUuid(uuidTrabajo)
                .orElseThrow(()-> new TrabajoNotFoundEx("ERROR: El trabajo ingresado no existe"));

        if (!esAdmin && !esElTecnico(trabajo, email) && !esElCliente(trabajo, email)) {
            throw new AccessDeniedException("No tiene permiso para ver este trabajo.");
        }
        return TrabajoMapper.toResponse(trabajo);
    }

    // ---------- auxiliares ----------

    private boolean esElTecnico(TrabajoEntity trabajo, String email) {
        return trabajo.getPresupuesto().getTecnico().getUsuario().getEmail().equals(email);
    }

    private boolean esElCliente(TrabajoEntity trabajo, String email) {
        return trabajo.getPresupuesto().getUsuario().getEmail().equals(email);
    }

    // Pendiente -> En proceso / Cancelado
    // En proceso -> Finalizado / Cancelado
    // Finalizado y Cancelado ya no cambian
    private void validarTransicion(EstadoTrabajo actual, EstadoTrabajo nuevo) {
        boolean permitido = switch (actual) {
            case Pendiente  -> nuevo == EstadoTrabajo.En_proceso || nuevo == EstadoTrabajo.Cancelado;
            case En_proceso -> nuevo == EstadoTrabajo.Finalizado || nuevo == EstadoTrabajo.Cancelado;
            case Finalizado, Cancelado -> false;
        };

        if (!permitido) {
            throw new TrabajoNotAcceptedEx("ERROR: No se puede pasar el trabajo de "
                    + actual + " a " + nuevo + ".");
        }
    }
}