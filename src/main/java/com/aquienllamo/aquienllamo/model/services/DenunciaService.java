package com.aquienllamo.aquienllamo.model.services;

import com.aquienllamo.aquienllamo.model.APIs.GoogleGmail.EmailService;
import com.aquienllamo.aquienllamo.model.Enum.EstadoDenunciaE;
import com.aquienllamo.aquienllamo.model.auth.Credentials.CredentialsEntity;
import com.aquienllamo.aquienllamo.model.auth.repositories.CredentialsRepository;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    private final EmailService emailService;
    private final CredentialsRepository credentialsRepository;

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
        nueva.setDenunciante(denunciante);
        nueva.setDenunciado(denunciado);

        if (denuncia.getFoto()!=null && !denuncia.getFoto().isEmpty()){
            try {
                nueva.setFoto(denuncia.getFoto().getBytes());
                nueva.setTipoFoto(denuncia.getFoto().getContentType());
            }catch (IOException ex){
                throw new ImageDataTypeNotFoundEx("Error al procesar la foto");
            }
        }

        emailService.enviarDenunciaAprobadaDenunciante(denunciante.getEmail());
        emailService.enviarDenunciaAprobadaDenunciado(denunciado.getEmail());
        emailService.enviarDenunciaAdmin("aquienllamoinfo@gmail.com", nueva.getUuid());
        denunciaRepository.save(nueva);
        return denunciaMapper.toResponse(nueva);
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
//    public DenunciaDTOResponse actualizarDenuncia(String uuid, DenunciaDTORequest denuncia){
//        DenunciaEntity nueva=denunciaRepository.findByUuid(uuid)
//                .orElseThrow(()-> new DenunciaNotFoundEx("no se encontro la denuncia con ese uuid"));
//        if (denuncia.getMotivoDenuncia()!=null){
//            nueva.setMotivoDenuncia(denuncia.getMotivoDenuncia());
//        }
//        if (denuncia.getFoto() != null && !denuncia.getFoto().isEmpty()) {
//            try {
//                nueva.setFoto(denuncia.getFoto().getBytes());
//                nueva.setTipoFoto(denuncia.getFoto().getContentType());
//            } catch (IOException ex) {
//                throw new ImageDataTypeNotFoundEx("Error al procesar la foto");
//            }
//        }
//        return denunciaMapper.toResponse(denunciaRepository.save(nueva));
//    }

    //asignar admin a denuncia
    public DenunciaDTOResponse asignarAdministrador(String uuidDenuncia, String uuidAdmin){
        DenunciaEntity denuncia=denunciaRepository.findByUuid(uuidDenuncia)
                .orElseThrow(()-> new DenunciaNotFoundEx("No se encontro la denuncia con ese uuid"));

        if (denuncia.getAdministrador()!=null){
            throw new AdminAsignadoDenunciaEx("La denuncia ya tiene un administrador asignado");
        }

        if (denuncia.getEstadoDenuncia()==EstadoDenunciaE.Rechazada || denuncia.getEstadoDenuncia()==EstadoDenunciaE.Aprobada){
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
    public DenunciaDTOResponse aprobarDenuncia(String uuidDenuncia, String mensaje){

        DenunciaEntity denuncia=denunciaRepository.findByUuid(uuidDenuncia)
                .orElseThrow(()-> new DenunciaNotFoundEx("No se encontro la denuncia con ese uuid"));

        if (denuncia.getEstadoDenuncia()==EstadoDenunciaE.Aprobada){
            throw new DenunciaResueltaEx("La denuncia ya esta aprobada.");
        }

        if (denuncia.getEstadoDenuncia()==EstadoDenunciaE.Rechazada){
            throw new DenunciaResueltaEx("La denuncia no se puede aprobar porque fue rechazada anteriormente.");
        }

        if (denuncia.getAdministrador() == null) {
            throw new AdministradorNotFoundEx("No hay administrador asignado a esta denuncia.");
        }

        UsuarioSecurity usuarioLogueado =
                (UsuarioSecurity) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();

        CredentialsEntity cred = credentialsRepository.findByUsername(usuarioLogueado.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Credencial no encontrada"));

        AdministradorEntity adminLogueado = cred.getAdministrador();

        if (adminLogueado == null) {
            throw new AdministradorNotFoundEx("La cuenta no pertenece a un administrador.");
        }

        if (!denuncia.getAdministrador().getUuid().equals(adminLogueado.getUuid())){
            throw new AdminAsignadoDenunciaEx("Solo el administrador asignado puede aprobar esta denuncia.");
        }

        denuncia.setEstadoDenuncia(EstadoDenunciaE.Aprobada);
        denuncia.setNotaDelAdmin(mensaje);
        denunciaRepository.save(denuncia);
        emailService.enviarDenunciaAprobadaDenunciante(denuncia.getDenunciante().getEmail());
        emailService.enviarDenunciaAprobadaDenunciado(denuncia.getDenunciado().getEmail());
        return denunciaMapper.toResponse(denuncia);
    }

    // rechazar denuncia
    public DenunciaDTOResponse rechazarDenuncia(String uuidDenuncia, String mensaje){
        DenunciaEntity denuncia = denunciaRepository.findByUuid(uuidDenuncia)
                .orElseThrow(() -> new DenunciaNotFoundEx("No se encontró una denuncia."));

        if (denuncia.getEstadoDenuncia() != EstadoDenunciaE.En_proceso){
            throw new DenunciaResueltaEx("La denuncia debe estar en proceso para ser resuelta.");
        }

        if (denuncia.getAdministrador() == null) {
            throw new AdministradorNotFoundEx("No hay administrador asignado a esta denuncia.");
        }
        UsuarioSecurity usuarioLogueado =
                (UsuarioSecurity) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();

        CredentialsEntity cred = credentialsRepository
                .findByUsername(usuarioLogueado.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Credencial no encontrada"));

        AdministradorEntity adminLogueado = cred.getAdministrador();

        if (adminLogueado == null) {
            throw new AdministradorNotFoundEx("La cuenta no pertenece a un administrador.");
        }

        if (!denuncia.getAdministrador().getUuid().equals(adminLogueado.getUuid())){
            throw new AdminAsignadoDenunciaEx("Solo el administrador asignado puede aprobar esta denuncia.");
        }

        denuncia.setEstadoDenuncia(EstadoDenunciaE.Rechazada);
        denuncia.setNotaDelAdmin(mensaje);
        denunciaRepository.save(denuncia);
        emailService.enviarDenunciaRechazadaDenunciante(denuncia.getDenunciante().getEmail());
        emailService.enviarDenunciaRechazadaDenunciado(denuncia.getDenunciado().getEmail());
        return denunciaMapper.toResponse(denuncia);
    }

    public List<DenunciaDTOResponse> denunciasMasViejasPrimero(){
        return denunciaRepository.findAllByOrderByFechaDenunciaAsc()
                .stream()
                .map(denunciaMapper::toResponse)
                .toList();
    }

    public List<DenunciaDTOResponse> denunciasMasNuevasPrimero(){
        return denunciaRepository.findAllByOrderByFechaDenunciaDesc()
                .stream()
                .map(denunciaMapper::toResponse)
                .toList();
    }

    // pendientes sin asignar
    public List<DenunciaDTOResponse> denunciasSinAsignar(){
        return denunciaRepository.findByAdministradorIsNull()
                .stream()
                .map(denunciaMapper::toResponse)
                .toList();
    }


    public List<DenunciaDTOResponse> misDenuncias(EstadoDenunciaE estado){

        UsuarioSecurity usuarioLogueado =
                (UsuarioSecurity) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();

        CredentialsEntity cred = credentialsRepository
                .findByUsername(usuarioLogueado.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Credencial no encontrada"));

        AdministradorEntity adminLogueado = cred.getAdministrador();

        if (adminLogueado == null) {
            throw new AdministradorNotFoundEx("La cuenta no pertenece a un administrador.");
        }
        return denunciaRepository
                .findByAdministradorUuidAndEstadoDenuncia(
                        adminLogueado.getUuid(),
                        estado
                )
                .stream()
                .map(denunciaMapper::toResponse)
                .toList();
    }
    
}
