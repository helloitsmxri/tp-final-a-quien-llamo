package com.aquienllamo.aquienllamo.entidades.mensaje.services;

import com.aquienllamo.aquienllamo.common.FileStorageService;
import com.aquienllamo.aquienllamo.entidades.chat.entity.ChatEntity;
import com.aquienllamo.aquienllamo.entidades.chat.exceptions.ChatNotFoundEx;
import com.aquienllamo.aquienllamo.entidades.chat.repository.ChatRepository;
import com.aquienllamo.aquienllamo.entidades.mensaje.dto.request.MensajeDTORequest;
import com.aquienllamo.aquienllamo.entidades.mensaje.dto.response.MensajeDTOResponse;
import com.aquienllamo.aquienllamo.entidades.mensaje.entity.MensajeEntity;
import com.aquienllamo.aquienllamo.entidades.mensaje.mapper.MensajeMapper;
import com.aquienllamo.aquienllamo.entidades.mensaje.repository.MensajeRepository;
import com.aquienllamo.aquienllamo.entidades.usuario.entities.UsuarioEntity;
import com.aquienllamo.aquienllamo.entidades.usuario.exceptions.UserNotFoundEx;
import com.aquienllamo.aquienllamo.entidades.usuario.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
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

    // Crear un mensaje. El emisor es el usuario logueado (sale del token, no del front).
    public MensajeDTOResponse crearMensaje(MensajeDTORequest dto, String emailLogueado) {
        ChatEntity chat = chatRepository.findByUuidChat(dto.getUuidChat())
                .orElseThrow(() -> new ChatNotFoundEx("No se encontro el chat"));

        UsuarioEntity sender = usuarioRepository.findByEmail(emailLogueado)
                .orElseThrow(() -> new UserNotFoundEx("No se encontro el usuario"));

        verificarPertenencia(chat, sender);

        String archivoUrl = null;
        String tipoArchivo = null;

        if (dto.getArchivo() != null && !dto.getArchivo().isEmpty()) {
            archivoUrl = fileStorageService.guardarArchivo(dto.getArchivo());
            String contentType = dto.getArchivo().getContentType();
            tipoArchivo = "application/pdf".equals(contentType) ? "pdf" : "imagen";
        }

        MensajeEntity mensaje = MensajeEntity.builder()
                .chat(chat)
                .sender(sender)
                .mensaje(dto.getMensaje())
                .archivoUrl(archivoUrl)
                .tipoArchivo(tipoArchivo)
                .build();

        return mensajeMapper.toResponse(mensajeRepository.save(mensaje));
    }

    // Mensajes de UN chat, solo si el usuario logueado participa en él
    public List<MensajeDTOResponse> listarPorChat(String uuidChat, String emailLogueado) {
        ChatEntity chat = chatRepository.findByUuidChat(uuidChat)
                .orElseThrow(() -> new ChatNotFoundEx("No se encontro el chat"));

        UsuarioEntity usuario = usuarioRepository.findByEmail(emailLogueado)
                .orElseThrow(() -> new UserNotFoundEx("No se encontro el usuario"));

        verificarPertenencia(chat, usuario);

        return mensajeRepository.findByChat_UuidChatOrderByFechaMensajeAsc(uuidChat)
                .stream()
                .map(mensajeMapper::toResponse)
                .toList();
    }

    public MensajeDTOResponse modificarMensaje(MensajeDTORequest dto) {
        MensajeEntity mensaje = mensajeRepository.findByUuidMensaje(dto.getUuidMensaje())
                .orElseThrow(() -> new ChatNotFoundEx("No se encontro el mensaje"));
        mensaje.setMensaje(dto.getMensaje());
        return mensajeMapper.toResponse(mensajeRepository.save(mensaje));
    }

    // Solo para administradores
    public List<MensajeDTOResponse> listarMensajes() {
        return mensajeRepository.findAll()
                .stream()
                .map(mensajeMapper::toResponse)
                .toList();
    }

    private void verificarPertenencia(ChatEntity chat, UsuarioEntity usuario) {
        boolean esElCliente = chat.getUsuario().getUuid().equals(usuario.getUuid());
        boolean esElTecnico = chat.getTecnico().getUsuario().getUuid().equals(usuario.getUuid());
        if (!esElCliente && !esElTecnico) {
            throw new AccessDeniedException("El usuario no pertenece a este chat");
        }
    }
}