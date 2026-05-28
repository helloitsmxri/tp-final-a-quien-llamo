package com.aquienllamo.aquienllamo.model.services;

import com.aquienllamo.aquienllamo.model.dtos.Request.MensajeDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.MensajeDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.MensajeEntity;
import com.aquienllamo.aquienllamo.model.exceptions.ChatNotFoundEx;
import com.aquienllamo.aquienllamo.model.mappers.MensajeMapper;
import com.aquienllamo.aquienllamo.model.repositories.MensajeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MensajeService {

    private final MensajeRepository mensajeRepository;
    private final MensajeMapper mensajeMapper;

    //crear un mensaje:
    public MensajeDTOResponse crearMensaje(MensajeDTORequest dto)
    {
        MensajeEntity mensaje= mensajeRepository.findByChat_UuidChat(dto.getUuidChat())
                .orElseThrow(()-> new ChatNotFoundEx("No se encontro el chat"));
        return mensajeMapper.toResponse(mensajeRepository.save(mensaje));
    }
    //modificar:
    public MensajeDTOResponse modificarMensaje(MensajeDTORequest dto)
    {
        MensajeEntity mensaje = mensajeRepository.findByChat_UuidChat(dto.getUuidChat())
                .orElseThrow(()-> new ChatNotFoundEx("No se encontro el chat"));
        mensaje.setMensaje(dto.getMensaje());
        return mensajeMapper.toResponse(mensajeRepository.save(mensaje));
    }
    //listar:
    public List<MensajeDTOResponse> listarMensajes()
    {
        return mensajeRepository.findAll()
                .stream()
                .map(mensajeMapper::toResponse)
                .toList();
    }
}
