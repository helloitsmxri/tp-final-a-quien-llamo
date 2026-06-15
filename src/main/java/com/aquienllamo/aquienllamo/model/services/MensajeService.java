package com.aquienllamo.aquienllamo.model.services;

import com.aquienllamo.aquienllamo.model.dtos.Request.MensajeDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.MensajeDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.ChatEntity;
import com.aquienllamo.aquienllamo.model.entities.MensajeEntity;
import com.aquienllamo.aquienllamo.model.entities.UsuarioEntity;
import com.aquienllamo.aquienllamo.model.exceptions.ChatNotFoundEx;
import com.aquienllamo.aquienllamo.model.exceptions.UserNotFoundEx;
import com.aquienllamo.aquienllamo.model.mappers.MensajeMapper;
import com.aquienllamo.aquienllamo.model.repositories.ChatRepository;
import com.aquienllamo.aquienllamo.model.repositories.MensajeRepository;
import com.aquienllamo.aquienllamo.model.repositories.UsuarioRepository;
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
    private final ChatRepository chatRepository;
    private final UsuarioRepository usuarioRepository;
    private final FileStorageService fileStorageService;

    //crear un mensaje:
    public MensajeDTOResponse crearMensaje(MensajeDTORequest dto)
    {
        ChatEntity chat = chatRepository.findByUuidChat(dto.getUuidChat())
                .orElseThrow(()-> new ChatNotFoundEx("No se encontro el chat"));

        UsuarioEntity sender = usuarioRepository.findByUuid(dto.getUuidSender())
                .orElseThrow(()-> new UserNotFoundEx("No se encontro el usuario"));

        String archivoUrl = null;
        String tipoArchivo = null;

        if (dto.getArchivo() != null && !dto.getArchivo().isEmpty()) {
            archivoUrl = fileStorageService.guardarArchivo(dto.getArchivo());
            String contentType = dto.getArchivo().getContentType();
            tipoArchivo = (contentType != null && contentType.equals("application/pdf")) ? "pdf" : "imagen";
        }
        MensajeEntity mensaje = MensajeEntity.builder()
                .chat(chat)
                .sender(sender)
                .mensaje(dto.getMensaje())
                .build();

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
