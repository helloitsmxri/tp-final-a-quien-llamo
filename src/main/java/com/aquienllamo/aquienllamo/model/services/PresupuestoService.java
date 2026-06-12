package com.aquienllamo.aquienllamo.model.services;

import com.aquienllamo.aquienllamo.model.Enum.EstadoPresupuestoE;
import com.aquienllamo.aquienllamo.model.Enum.EstadoTrabajo;
import com.aquienllamo.aquienllamo.model.dtos.Request.PresupuestoDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.PresupuestoDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.*;
import com.aquienllamo.aquienllamo.model.exceptions.PresupuestoNotFoundEx;
import com.aquienllamo.aquienllamo.model.exceptions.TecnicoNotFoundEx;
import com.aquienllamo.aquienllamo.model.exceptions.UserNotFoundEx;
import com.aquienllamo.aquienllamo.model.mappers.PresupuestoMapper;
import com.aquienllamo.aquienllamo.model.repositories.*;
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
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional // es esencial p/bdd
public class PresupuestoService {
    private final PresupuestoMapper presupuestoMapper;
    private final PresupuestoRepository presupuestoRepository;
    private final UsuarioRepository usuarioRepository;
    private final TecnicoRepository tecnicoRepository;
    private final MensajeRepository mensajeRepository;
    private final ChatRepository chatRepository;
    private final TrabajoRepository trabajoRepository;
    // acá iría un private final de CHAT REPOSITORY Y MENSAJE REPOSITORY!!!! todavía está en desarrollo

    // Registrar un nuevo presupuesto en el sistema
    public PresupuestoDTOResponse createPresupuesto(PresupuestoDTORequest dto, String uuidChat, String emailTech) {

        ChatEntity chat = chatRepository.findByUuidChat(uuidChat)
                .orElseThrow(() -> new RuntimeException("Chat no encontrado"));

        TecnicoEntity tecnico = chat.getTecnico();
        UsuarioEntity usuario = chat.getUsuario();

        // seguridad
        if (!chat.getTecnico()
                .getUsuario()
                .getEmail()
                .equals(emailTech)) {

            throw new RuntimeException("No autorizado");
        }

        PresupuestoEntity presupuesto = PresupuestoEntity.builder()
                .chat(chat)
                .usuario(usuario)
                .tecnico(tecnico)
                .precioEstimado(dto.getPrecioEstimado())
                .descripcionPresupuesto(dto.getDescripcionPresupuesto())
                .estado(EstadoPresupuestoE.Pendiente)
                // no pongo fecha xq lo hace solo.
                .build();

        PresupuestoEntity saved = presupuestoRepository.save(presupuesto);

        MensajeEntity mensaje = new MensajeEntity();
        mensaje.setChat(chat);
        // no establezco fecha ni uuid porq entity lo hace solo.
        mensaje.setSender(tecnico.getUsuario());

        mensaje.setMensaje(
                "¡Hola! Creé un presupuesto según lo acordado: $" + presupuesto.getPrecioEstimado()
                        + " Descripción y razones para el presupuesto: " + presupuesto.getDescripcionPresupuesto()
        );

        mensajeRepository.save(mensaje);

        return presupuestoMapper.toResponse(saved);
    }

    // rechazar presupuesto
    public void rechazarPresupuesto(String uuid, String emailUser){
        // verificar q el presupuesto existe:
        PresupuestoEntity presupuesto = presupuestoRepository.findByUuid(uuid)
                .orElseThrow(() -> new PresupuestoNotFoundEx("No se encontró el presupuesto"));

        // verificar q el usuario es el dueño d ese presupuesto
        if (!presupuesto.getUsuario().getEmail().equals(emailUser)){
            throw new RuntimeException("No tiene permisos para cancelar este presupuesto.");
        }

        presupuesto.setEstado(EstadoPresupuestoE.Rechazado);
        presupuestoRepository.save(presupuesto);

        MensajeEntity mensaje = new MensajeEntity();
        mensaje.setChat(presupuesto.getChat());
        mensaje.setSender(presupuesto.getUsuario());
        // no establezco fecha ni uuid porq entity lo hace solo.
        mensaje.setMensaje("Por el momento no voy a continuar con este presupuesto." +
                "Gracias por el tiempo que te tomaste en elaborarlo." +
                "ATENCIÓN: Este es un mensaje automatizado.");

        mensajeRepository.save(mensaje);
    }

    // aceptar presupuesto
    public void aceptarPresupuesto(String uuid, String emailUser){
        // verificar q existe el presupuesto:
        PresupuestoEntity presupuesto = presupuestoRepository.findByUuid(uuid)
                .orElseThrow(() -> new PresupuestoNotFoundEx("No se encontró el presupuesto solicitado"));

        // verificar q el user corresponde al presupuesto:
        if (!presupuesto.getUsuario().getEmail().equals(emailUser)){
            throw new RuntimeException("No se puede aceptar un presupuesto ajeno.");
        }

        if (presupuesto.getEstado() != EstadoPresupuestoE.Pendiente) {
            throw new RuntimeException("Solo se pueden aceptar presupuestos pendientes.");
        }

        presupuesto.setEstado(EstadoPresupuestoE.Aceptado);
        presupuestoRepository.save(presupuesto);

        // quité lo de crearlo automáticamente porque trabajo pide muchos datos y no los puedo proveer yo.

        // crear un mensaje en el chat para avisar q se aceptó el trabajo
        MensajeEntity mensaje = new MensajeEntity();
        mensaje.setChat(presupuesto.getChat());
        mensaje.setSender(presupuesto.getUsuario());
        mensaje.setMensaje("¡Encantad@ de trabajar con vos! Arreglemos un horario y una fecha."+
        "ATENCIÓN: Este es un mensaje automatizado.");
        // no establezco fecha ni uuid porq entity lo hace solo.

        mensajeRepository.save(mensaje);
    }

    // cancelar presupuesto siendo técnico
    public void cancelarPresupuesto (String uuid, String emailTech){
        PresupuestoEntity presupuesto = presupuestoRepository.findByUuid(uuid)
                .orElseThrow(() -> new PresupuestoNotFoundEx("No se encontró el presupuesto solicitado"));

        if (!presupuesto.getTecnico().getUsuario().getEmail().equals(emailTech)){
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
