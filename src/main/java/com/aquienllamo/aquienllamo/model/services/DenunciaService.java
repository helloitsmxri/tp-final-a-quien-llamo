package com.aquienllamo.aquienllamo.model.services;

import com.aquienllamo.aquienllamo.model.Enum.EstadoDenunciaE;
import com.aquienllamo.aquienllamo.model.details.UsuarioSecurity;
import com.aquienllamo.aquienllamo.model.dtos.Request.DenunciaDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.DenunciaDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.AdministradorEntity;
import com.aquienllamo.aquienllamo.model.entities.ChatEntity;
import com.aquienllamo.aquienllamo.model.entities.DenunciaEntity;
import com.aquienllamo.aquienllamo.model.entities.UsuarioEntity;
import com.aquienllamo.aquienllamo.model.exceptions.*;
import com.aquienllamo.aquienllamo.model.mappers.DenunciaMapper;
import com.aquienllamo.aquienllamo.model.repositories.AdministradorRepository;
import com.aquienllamo.aquienllamo.model.repositories.ChatRepository;
import com.aquienllamo.aquienllamo.model.repositories.DenunciaRepository;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import com.aquienllamo.aquienllamo.model.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class DenunciaService {
    private final DenunciaRepository denunciaRepository;
    private final DenunciaMapper denunciaMapper;
    private final UsuarioRepository usuarioRepository;
    private final ChatRepository chatRepository;
    private final AdministradorRepository administradorRepository;

    //crear
    public DenunciaDTOResponse crearDenuncia(DenunciaDTORequest denuncia, String uuidChat){
        //obtener usuario logueado
        UsuarioSecurity usuarioLogueado=(UsuarioSecurity) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        UsuarioEntity denunciante=usuarioRepository.findByUuid(usuarioLogueado.getUuid())
                .orElseThrow(()-> new UserNotFoundEx("no se encontro el usuario con ese uuid"));

        //obtener el chat y sacar el denunciado
        ChatEntity chat=chatRepository.findByUuidChat(uuidChat)
                .orElseThrow(()-> new ChatNotFoundEx("el chat con ese uuid no se encontro"));

        UsuarioEntity denunciado;
        if (chat.getUsuario().getUuid().equals(usuarioLogueado.getUuid())) {
            denunciado=chat.getTecnico().getUsuario();
        }else {
            denunciado=chat.getUsuario();
        }

        DenunciaEntity nueva=denunciaMapper.toEntity(denuncia);
        nueva.setEstadoDenuncia(EstadoDenunciaE.Pendiente);
        //denunciante
        nueva.setNombreDenunciante(denunciante.getNombre());
        nueva.setApellidoDenunciante(denunciante.getApellido());
        nueva.setDniDenunciante(denunciante.getDni());
        nueva.setTelefonoDenunciante(denunciante.getTelefono());
        //denunciado
        nueva.setNombreDenunciado(denunciado.getNombre());
        nueva.setApellidoDenunciado(denunciado.getApellido());
        nueva.setDniDenunciado(denunciado.getDni());
        nueva.setTelefonoDenunciado(denunciado.getTelefono());

        nueva.setFechaDenuncia(LocalDateTime.now());

        if (denuncia.getFoto()!=null && !denuncia.getFoto().isEmpty()){
            try {
                nueva.setFoto(denuncia.getFoto().getBytes());
                nueva.setTipoFoto(denuncia.getFoto().getContentType());
            }catch (IOException ex){
                throw new ImageDataTypeNotFoundEx("Error al procesar la foto");
            }
        }
        return denunciaMapper.toResponse(denunciaRepository.save(nueva));
    }

    //listar denuncias
    public List<DenunciaDTOResponse> listarDenuncias(){
        return denunciaRepository.findAll()
                .stream()
                .map(denunciaMapper::toResponse)
                .toList();
    }

    //buscar por uuid
    public DenunciaDTOResponse buscarPorUuid(String uuid){
        return denunciaRepository.findByUuid(uuid)
                .map(denunciaMapper::toResponse)
                .orElseThrow(()-> new DenunciaNotFoundEx("no se encontro la denuncia con ese uuid"));
    }

    //listar por estado
    public List<DenunciaDTOResponse> listarPorEstado(EstadoDenunciaE estado){
        return denunciaRepository.findByEstadoDenuncia(estado)
                .stream()
                .map(denunciaMapper::toResponse)
                .toList();
    }

    //eliminar
    public void eliminarDenuncia(String uuid){
        DenunciaEntity denuncia=denunciaRepository.findByUuid(uuid)
                .orElseThrow(()-> new DenunciaNotFoundEx("no se encontro la denuncia con ese uuid"));
        denunciaRepository.delete(denuncia);
    }

    //actualizar
    public DenunciaDTOResponse actualizarDenuncia(String uuid, DenunciaDTORequest denuncia){
        DenunciaEntity nueva=denunciaRepository.findByUuid(uuid)
                .orElseThrow(()-> new DenunciaNotFoundEx("no se encontro la denuncia con ese uuid"));
        if (denuncia.getMotivoDenuncia()!=null){
            nueva.setMotivoDenuncia(denuncia.getMotivoDenuncia());
        }
        if (denuncia.getFoto() != null && !denuncia.getFoto().isEmpty()) {
            try {
                nueva.setFoto(denuncia.getFoto().getBytes());
                nueva.setTipoFoto(denuncia.getFoto().getContentType());
            } catch (IOException ex) {
                throw new ImageDataTypeNotFoundEx("Error al procesar la foto");
            }
        }
        return denunciaMapper.toResponse(denunciaRepository.save(nueva));
    }

    //asignar admin a denuncia
    public DenunciaDTOResponse asignarAdministrador(String uuidDenuncia, String uuidAdmin){
        DenunciaEntity denuncia=denunciaRepository.findByUuid(uuidDenuncia)
                .orElseThrow(()-> new DenunciaNotFoundEx("No se encontro la denuncia con ese uuid"));

        if (denuncia.getAdministrador()!=null){
            throw new AdminAsignadoDenunciaEx("La denuncia ya tiene un administrador asignado");
        }

        if (denuncia.getEstadoDenuncia()==EstadoDenunciaE.Finalizado || denuncia.getEstadoDenuncia()==EstadoDenunciaE.Cancelado){
            throw new DenunciaResueltaEx("No se puede asignar una denuncia ya resuelta.");
        }

        AdministradorEntity admin=administradorRepository.findByUuid(uuidAdmin)
                .orElseThrow(()-> new AdministradorNotFoundEx("No se encontro el administrador con ese uuid"));

        denuncia.setAdministrador(admin);
        denuncia.setEstadoDenuncia(EstadoDenunciaE.En_proceso);
        return denunciaMapper.toResponse(denunciaRepository.save(denuncia));
    }

    //mostrar denuncias sin admin asignado
    public List<DenunciaDTOResponse> obtenerDenunciasSinAsignar(){
        return denunciaRepository.findByAdministradorIsNull()
                .stream()
                .map(denunciaMapper::toResponse)
                .toList();
    }

    //aprobar denuncia
    public DenunciaDTOResponse aprobarDenuncia(String uuidDenuncia){
        DenunciaEntity denuncia=denunciaRepository.findByUuid(uuidDenuncia)
                .orElseThrow(()-> new DenunciaNotFoundEx("No se encontro la denuncia con ese uuid"));

        if (denuncia.getEstadoDenuncia()==EstadoDenunciaE.Finalizado){
            throw new DenunciaResueltaEx("La denuncia ya esta aprobada.");
        }

        if (denuncia.getEstadoDenuncia()==EstadoDenunciaE.Cancelado){
            throw new DenunciaResueltaEx("La denuncia no se puede aprobar porque fue rechazada anteriormente.");
        }

        denuncia.setEstadoDenuncia(EstadoDenunciaE.Finalizado);
        denunciaRepository.save(denuncia);
        //email de notificar al denunciado
        return denunciaMapper.toResponse(denuncia);
    }
    
}
