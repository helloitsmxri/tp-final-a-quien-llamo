package com.aquienllamo.aquienllamo.model.services;

import com.aquienllamo.aquienllamo.model.Enum.EstadoPresupuestoE;
import com.aquienllamo.aquienllamo.model.dtos.Request.PresupuestoDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.PresupuestoDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.PresupuestoEntity;
import com.aquienllamo.aquienllamo.model.entities.TecnicoEntity;
import com.aquienllamo.aquienllamo.model.entities.UsuarioEntity;
import com.aquienllamo.aquienllamo.model.exceptions.PresupuestoNotFoundEx;
import com.aquienllamo.aquienllamo.model.exceptions.TecnicoNotFoundEx;
import com.aquienllamo.aquienllamo.model.exceptions.UserNotFoundEx;
import com.aquienllamo.aquienllamo.model.mappers.PresupuestoMapper;
import com.aquienllamo.aquienllamo.model.repositories.PresupuestoRepository;
import com.aquienllamo.aquienllamo.model.repositories.TecnicoRepository;
import com.aquienllamo.aquienllamo.model.repositories.UsuarioRepository;
import com.aquienllamo.aquienllamo.model.specifications.PresupuestoSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.domain.PredicateSpecification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional // es esencial p/bdd
public class PresupuestoService {
    private final PresupuestoMapper presupuestoMapper;
    private final PresupuestoRepository presupuestoRepository;
    private final UsuarioRepository usuarioRepository;
    private final TecnicoRepository tecnicoRepository;
    // acá iría un private final de CHAT REPOSITORY Y MENSAJE REPOSITORY!!!! todavía está en desarrollo

    // no llamo a especificación xq tiene metodos static

    // Registrar un nuevo presupuesto en el sistema
    public PresupuestoDTOResponse createPresupuesto (PresupuestoDTORequest dto, String uuidTecnico){

        // acá el token del técnicop
        TecnicoEntity technician = tecnicoRepository.findByUuid(uuidTecnico)
                .orElseThrow(() -> new TecnicoNotFoundEx("No se encontró al técnico"));

        // acá va el usuario:
        UsuarioEntity user = usuarioRepository.findByUuid(dto.getUuidUsuario())
                .orElseThrow(() -> new UserNotFoundEx("No se encontró el usuario."));

        // verificamos q tecnico no sea el mismo uuid de user (puede pasar)
        if (technician.getUsuario().getUuid().equals(user.getUuid())) {
            throw new RuntimeException("No puedes enviarte un presupuesto a ti mismo.");
        }

        // se setea
        PresupuestoEntity presupuesto = presupuestoMapper.toEntity(dto);
        presupuesto.setUsuario(user);
        presupuesto.setTecnico(technician);

        presupuesto.setFechaRealizado(LocalDateTime.now());

//        cuando las chicas hagan chat y mensaje hago esto:
//        ChatEntity chat = chatRepository.findByUuid(uuidChat)
//                .orElseThrow(() -> new RuntimeException("Chat no encontrado"));
//
//        MensajeEntity mensajeSistema = new MensajeEntity();
//        mensajeSistema.setUuid(UUID.randomUUID().toString());
//        mensajeSistema.setChat(chat);
//        mensajeSistema.setFechaMensaje(LocalDateTime.now());
//        mensajeSistema.setMensaje("Hola! Ya diseñé un presupuesto. Veríficalo, y acéptalo si estás de acuerdo: $" + presupuesto.getPrecioEstimado());
//
//        mensajeRepository.save(mensajeSistema);

        // podríamos setear un estado a pendiente...
        presupuesto.setEstado(EstadoPresupuestoE.Pendiente);
        return presupuestoMapper.toResponse(presupuestoRepository.save(presupuesto));

    }

    // rechazar presupuesto
    public void rechazarPresupuesto(String uuid, String uuidUser){
        // verificar q el presupuesto existe:
        PresupuestoEntity presupuesto = presupuestoRepository.findByUuid(uuid)
                .orElseThrow(() -> new PresupuestoNotFoundEx("No se encontró el presupuesto"));

        // verificar q el usuario es el dueño d ese presupuesto
        if (!presupuesto.getUsuario().getUuid().equals(uuidUser)){
            throw new RuntimeException("No tiene permisos para cancelar este presupuesto.");
        }

        presupuesto.setEstado(EstadoPresupuestoE.Rechazado);
        presupuestoRepository.save(presupuesto);
    }

    // aceptar presupuesto
    public void aceptarPresupuesto(String uuid, String uuidUser){
        // verificar q existe el presupuesto:
        PresupuestoEntity presupuesto = presupuestoRepository.findByUuid(uuid)
                .orElseThrow(() -> new PresupuestoNotFoundEx("No se encontró el presupuesto solicitado"));

        // verificar q el uuid user corresponde al presupuesto:
        if (!presupuesto.getUsuario().getUuid().equals(uuidUser)){
            throw new RuntimeException("No se puede aceptar un presupuesto ajeno.");
        }

        presupuesto.setEstado(EstadoPresupuestoE.Aceptado);
        presupuestoRepository.save(presupuesto);
    }

    // cancelar presupuesto siendo técnico
    public void cancelarPresupuesto (String uuid, String uuidTecnico){
        PresupuestoEntity presupuesto = presupuestoRepository.findByUuid(uuid)
                .orElseThrow(() -> new PresupuestoNotFoundEx("No se encontró el presupuesto solicitado"));

        if (!presupuesto.getTecnico().getUuid().equals(uuidTecnico)){
            throw new RuntimeException("No puede cancelar un presupuesto que no sea suyo.");
        }

        if (presupuesto.getEstado() == EstadoPresupuestoE.Aceptado){
            throw new RuntimeException("No se puede cancelar un presupuesto que ya ha sido aceptado.");
        }

        presupuesto.setEstado(EstadoPresupuestoE.Cancelado);
        presupuestoRepository.save(presupuesto);
    }

    // mostrar todos los presupuestos (creo q esto puede servir para los admins):
    public List<PresupuestoDTOResponse> getAllPresupuestos(){
        return presupuestoRepository.findAll()
                .stream()
                .map(presupuestoMapper::toResponse)
                .toList();
    }

    // mostrar presupuestos
    @Transactional(readOnly = true)
    public List<PresupuestoDTOResponse> buscarPresupuestosCompleto(String nombre, String apellido, BigDecimal min, BigDecimal max, LocalDate fecha) {

        PredicateSpecification<PresupuestoEntity> spec = PredicateSpecification.allOf(
                PresupuestoSpecifications.presupuestoDeTalPersonaNombre(nombre),
                PresupuestoSpecifications.presupuestoDeTalPersonaApellido(apellido),
                PresupuestoSpecifications.presupuestoGreaterThan(min),
                PresupuestoSpecifications.presupuestoLesserThan(max),
                PresupuestoSpecifications.presupuestoDeTalFecha(fecha)
        );

        // Agregamos el Sort para que los más nuevos salgan primero
        return presupuestoRepository.findAll(spec)
                .stream()
                .map(presupuestoMapper::toResponse)
                .toList();
    }

    // ver presupuesto detallado:
    public PresupuestoDTOResponse obtenerPorUuid(String uuidPresupuesto){
        return presupuestoRepository.findByUuid(uuidPresupuesto)
                .map(presupuestoMapper::toResponse)
                .orElseThrow(() -> new PresupuestoNotFoundEx("No se encontró el presupuesto"));
    }
}
