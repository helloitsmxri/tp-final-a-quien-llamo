package com.aquienllamo.aquienllamo.model.services;

import com.aquienllamo.aquienllamo.model.APIs.GoogleGmail.EmailService;
import com.aquienllamo.aquienllamo.model.auth.Credentials.CredentialsEntity;
import com.aquienllamo.aquienllamo.model.auth.permissions.RoleEntity;
import com.aquienllamo.aquienllamo.model.auth.permissions.RolesUser;
import com.aquienllamo.aquienllamo.model.auth.repositories.CredentialsRepository;
import com.aquienllamo.aquienllamo.model.auth.repositories.RoleRepository;
import com.aquienllamo.aquienllamo.model.dtos.Request.TecnicoDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Request.UsuarioDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.TecnicoDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.TecnicoEntity;
import com.aquienllamo.aquienllamo.model.entities.UsuarioEntity;
import com.aquienllamo.aquienllamo.model.exceptions.*;
import com.aquienllamo.aquienllamo.model.mappers.TecnicoMapper;
import com.aquienllamo.aquienllamo.model.mappers.UsuarioMapper;
import com.aquienllamo.aquienllamo.model.repositories.EspecialidadRepository;
import com.aquienllamo.aquienllamo.model.repositories.HabilidadRepository;
import com.aquienllamo.aquienllamo.model.repositories.TecnicoRepository;
import com.aquienllamo.aquienllamo.model.repositories.UsuarioRepository;
import com.aquienllamo.aquienllamo.model.specifications.TecnicoSpecifications;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TecnicoService {
    private final TecnicoRepository tecnicoRepository;
    private final UsuarioRepository usuarioRepository;
    private final HabilidadRepository  habilidadRepository;
    private final EspecialidadRepository especialidadRepository;
    private final TecnicoMapper tecnicoMapper;
    private final CredentialsRepository credentialsRepository;
    private final RoleRepository roleRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    //registrar usuario como tecnico
    public TecnicoDTOResponse registrarTecnico(TecnicoDTORequest dto, String uuidUsuario){
        UsuarioEntity user = usuarioRepository.findByUuid(uuidUsuario)
                .orElseThrow(()-> new UserNotFoundEx("no se encontro el usuario con ese uuid."));

        if (tecnicoRepository.existsByUsuario_Uuid(uuidUsuario)){
            throw new TecnicoAlreadyExistsEx("El usuario ya esta registrado como técnico.");
        }

        if (tecnicoRepository.existsByCuit(dto.getCuit())){
            throw new DuplicateCuitEx("El cuit ya existe.");
        }

        //validar que las habilidades existan
        if (dto.getUuidHabilidades().stream().anyMatch(uuid -> !habilidadRepository.existsByUuid(uuid))) {
            throw new HabilidadNotFoundEx("Una o más habilidades no existen.");
        }

        //validar que las especialidades existan
        if (dto.getUuidEspecialidades().stream().anyMatch(uuid -> !especialidadRepository.existsByUuid(uuid))) {
            throw new EspecialidadNotFoundEx("Una o más especialidades no existen.");
        }

        TecnicoEntity tecnico=tecnicoMapper.toEntity(dto);
        tecnico.setUsuario(user);
        tecnico.setHabilidades(habilidadRepository.findAllByUuidIn(dto.getUuidHabilidades()));
        tecnico.setEspecialidades(especialidadRepository.findAllByUuidIn(dto.getUuidEspecialidades()));

        //guardar tecnico
        TecnicoEntity tecnicoGuardado=tecnicoRepository.save(tecnico);

        //buscar rol tecnico
        RoleEntity roleTecnico=roleRepository.findByRole(RolesUser.ROLE_TECNICO)
                .orElseThrow(()-> new RoleNotFoundEx("No existe el ROLE_TECNICO."));

        //buscar credenciales del usuario
        CredentialsEntity credencial=credentialsRepository.findByUsuario_Uuid(uuidUsuario)
                .orElseThrow(()-> new CredentialsNotFoundEx("No se encontraron las credenciales del usuario."));

        //asignar rol a tecnico
        credencial.getRoles().add(roleTecnico);

        //guardar credenciales actualizadas
        credentialsRepository.save(credencial);
        emailService.enviarBienvenida2(tecnicoGuardado.getUsuario().getEmail(), tecnicoGuardado.getUsuario().getNombre());
        return tecnicoMapper.toResponse(tecnicoGuardado);
    }

    //registrar un tecnico nuevo
    public TecnicoDTOResponse registrarTecnicoNuevo(UsuarioDTORequest usuarioDto, TecnicoDTORequest tecnicoDto){

        // validar usuario
        if (usuarioRepository.existsByDni(usuarioDto.getDni())){
            throw new UserFoundEx("El documento " + usuarioDto.getDni() + " ya se encuentra asociado.");
        }

        if (usuarioRepository.existsByEmail(usuarioDto.getEmail())){
            throw new UserFoundEx("El correo " + usuarioDto.getEmail() + " ya esta en uso.");
        }

        // validar cuit
        if (tecnicoRepository.existsByCuit(tecnicoDto.getCuit())){
            throw new DuplicateCuitEx("El cuit ya existe.");
        }

        //validar edad
        if (Period.between(usuarioDto.getFechaNacimiento(), LocalDate.now()).getYears() < 18){
            throw new MinorFoundEx("No se pueden registrar menores de 18 años.");
        }

        // validar habilidades
        if (tecnicoDto.getUuidHabilidades().stream().anyMatch(uuid -> !habilidadRepository.existsByUuid(uuid))) {

            throw new HabilidadNotFoundEx("Una o más habilidades no existen.");
        }

        // validar especialidades
        if (tecnicoDto.getUuidEspecialidades().stream().anyMatch(uuid -> !especialidadRepository.existsByUuid(uuid))) {

            throw new EspecialidadNotFoundEx("Una o más especialidades no existen.");
        }

        // crear usuario
        UsuarioEntity usuario = usuarioMapper.toEntity(usuarioDto);
        usuario.setClave(passwordEncoder.encode(usuarioDto.getClave()));
        // procesar foto
        processImage(usuario, usuarioDto);

        usuarioRepository.save(usuario);

        // buscar roles
        RoleEntity roleUsuario = roleRepository.findByRole(RolesUser.ROLE_USUARIO)
                .orElseThrow(() -> new RoleNotFoundEx("No existe ROLE_USUARIO."));

        RoleEntity roleTecnico = roleRepository.findByRole(RolesUser.ROLE_TECNICO)
                .orElseThrow(() -> new RoleNotFoundEx("No existe ROLE_TECNICO."));

        // crear credenciales
        CredentialsEntity credencial = CredentialsEntity.builder()
                        .username(usuario.getEmail())
                        .clave(usuario.getClave())
                        .enabled(true)
                        .usuario(usuario)
                        .build();

        credencial.getRoles().add(roleUsuario);
        credencial.getRoles().add(roleTecnico);

        credentialsRepository.save(credencial);

        // crear tecnico
        TecnicoEntity tecnico = tecnicoMapper.toEntity(tecnicoDto);
        tecnico.setUsuario(usuario);
        tecnico.setHabilidades(habilidadRepository.findAllByUuidIn(tecnicoDto.getUuidHabilidades()));
        tecnico.setEspecialidades(especialidadRepository.findAllByUuidIn(tecnicoDto.getUuidEspecialidades()));

        TecnicoEntity tecnicoGuardado = tecnicoRepository.save(tecnico);
        emailService.enviarBienvenida2(usuario.getEmail(), usuario.getNombre());

        return tecnicoMapper.toResponse(tecnicoGuardado);
    }

    // procesar foto
    private void processImage(UsuarioEntity user, UsuarioDTORequest request){
        if (request.getFoto() != null && !request.getFoto().isEmpty()){
            try{
                String tipoImagen = request.getFoto().getContentType();
                if (tipoImagen == null || !tipoImagen.startsWith("image/")){
                    throw new ImageDataTypeNotFoundEx("Formato no válido de imagen");
                }
                user.setTipoImagen(tipoImagen);
                user.setFoto(request.getFoto().getBytes());
            }catch (IOException errorImagen){
                throw new RuntimeException("Hubo un error con la imagen");
            }
        }else{
            user.setTipoImagen("None");
            user.setFoto(null);
        }
    }

    //listar todos los tecnicos
    public List<TecnicoDTOResponse> getAllTecnicos(){
        return tecnicoRepository.findAll()
                .stream()
                .map(tecnicoMapper::toResponse)
                .toList();
    }

    //buscar tecnico por uuid
    public TecnicoDTOResponse getTecnicoByUuid(String uuid){
        return tecnicoMapper.toResponse(
                tecnicoRepository.findByUuid(uuid)
                        .orElseThrow(()-> new TecnicoNotFoundEx("el técnico con ese uuid no se encuentra."))
        );
    }

    //filtrar por habilidad
    public List<TecnicoDTOResponse> obtenerTecnicosPorHabilidad(String uuid){
        return tecnicoRepository.findByHabilidades_Uuid(uuid)
                .stream()
                .map(tecnicoMapper::toResponse)
                .toList();
    }

    //filtrar por especialidad
    public List<TecnicoDTOResponse> obtenerTecnicosPorEspecialidad(String uuid){
        return tecnicoRepository.findByEspecialidades_Uuid(uuid)
                .stream()
                .map(tecnicoMapper::toResponse)
                .toList();
    }

    //filtrar por nombres
    public List<TecnicoDTOResponse> obtenerTecnicosPorNombre(String nombre){
        return tecnicoRepository.findByUsuario_NombreContainingIgnoreCase(nombre)
                .stream()
                .map(tecnicoMapper::toResponse)
                .toList();
    }

    //filtrar por rubros
    public List<TecnicoDTOResponse> getTecnicosByRubro(String uuidRubro){
        return tecnicoRepository.findByEspecialidades_Rubros_Uuid(uuidRubro)
                .stream()
                .map(tecnicoMapper::toResponse)
                .toList();
    }

    //buscar tecnicos
    public List<TecnicoDTOResponse> buscarTecnicos(String nombre, String apellido, String habilidad, String especialidad, LocalDateTime fecha) {
        Specification<TecnicoEntity> spec = Specification
                .where(TecnicoSpecifications.porNombre(nombre))
                .and(TecnicoSpecifications.porApellido(apellido))
                .and(TecnicoSpecifications.porHabilidad(habilidad))
                .and(TecnicoSpecifications.porEspecialidad(especialidad))
                .and(TecnicoSpecifications.porFechaRegistro(fecha));

        return tecnicoRepository.findAll(spec)
                .stream()
                .map(tecnicoMapper::toResponse)
                .toList();
    }

    //actualizar tecnico
    public TecnicoDTOResponse updateTecnico(String uuid, TecnicoDTORequest dto){
        TecnicoEntity tecnico=tecnicoRepository.findByUuid(uuid)
                .orElseThrow(()-> new TecnicoNotFoundEx("el técnico con ese uuid no existe."));

        // validar que las habilidades existan
        if(dto.getUuidHabilidades().stream().anyMatch(uuidH -> !habilidadRepository.existsByUuid(uuidH))) {
            throw new HabilidadNotFoundEx("Una o más habilidades no existen.");
        }

        // validar que las especialidades existan
        if(dto.getUuidEspecialidades().stream().anyMatch(uuidE -> !especialidadRepository.existsByUuid(uuidE))) {
            throw new EspecialidadNotFoundEx("Una o más especialidades no existen.");
        }

        //validar cuit
        if (!tecnico.getCuit().equals(dto.getCuit()) && tecnicoRepository.existsByCuit(dto.getCuit())) {
            throw new DuplicateCuitEx("El cuit ya existe.");
        }

        tecnico.setCuit(dto.getCuit());
        tecnico.setDescripcionTrabajo(dto.getDescripcionTrabajo());
        tecnico.setProyectos(dto.getProyectos());
        tecnico.setHabilidades(habilidadRepository.findAllByUuidIn(dto.getUuidHabilidades()));
        tecnico.setEspecialidades(especialidadRepository.findAllByUuidIn(dto.getUuidEspecialidades()));
        return tecnicoMapper.toResponse(tecnicoRepository.save(tecnico));
    }

    //eliminar tecnico
    public void deleteTecnico(String uuid){
        TecnicoEntity tecnico=tecnicoRepository.findByUuid(uuid)
                .orElseThrow(()-> new TecnicoNotFoundEx("el técnico con ese uuid no se encuentra."));
        tecnicoRepository.delete(tecnico);
    }

    /*
    //listar tecnicos ordenados por fecha de registros del mas nuevo al mas viejo
    public List<TecnicoDTOResponse> getTecnicosOrderByFechaRegistroDesc(){
        return tecnicoRepository.findAllByOrderByUsuario_FechaRegistroDesc()
                .stream()
                .map(tecnicoMapper::toResponse)
                .toList();
    }

    //listar tecnicos ordenados por fecha de registros del mas viejo al mas nuevo
    public List<TecnicoDTOResponse> getTecnicosOrderByFechaRegistroAsc(){
        return tecnicoRepository.findAllByOrderByUsuario_FechaRegistroAsc()
                .stream()
                .map(tecnicoMapper::toResponse)
                .toList();
    }

     */
}
