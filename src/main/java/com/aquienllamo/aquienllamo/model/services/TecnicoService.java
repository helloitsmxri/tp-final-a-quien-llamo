package com.aquienllamo.aquienllamo.model.services;

import com.aquienllamo.aquienllamo.model.dtos.Request.TecnicoDTORequest;
import com.aquienllamo.aquienllamo.model.dtos.Response.TecnicoDTOResponse;
import com.aquienllamo.aquienllamo.model.entities.TecnicoEntity;
import com.aquienllamo.aquienllamo.model.entities.UsuarioEntity;
import com.aquienllamo.aquienllamo.model.exceptions.*;
import com.aquienllamo.aquienllamo.model.mappers.TecnicoMapper;
import com.aquienllamo.aquienllamo.model.repositories.EspecialidadRepository;
import com.aquienllamo.aquienllamo.model.repositories.HabilidadRepository;
import com.aquienllamo.aquienllamo.model.repositories.TecnicoRepository;
import com.aquienllamo.aquienllamo.model.repositories.UsuarioRepository;
import com.aquienllamo.aquienllamo.model.specifications.TecnicoSpecifications;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    //registrar usuario como tecnico
    public TecnicoDTOResponse registrarTecnico(TecnicoDTORequest dto, String uuidUsuario){
        UsuarioEntity user = usuarioRepository.findByUuid(uuidUsuario)
                .orElseThrow(UserNotFoundEx::new);

        if (tecnicoRepository.existsByUsuario_idUsuario(user.getIdUsuario())){
            throw new TecnicoAlreadyExistsEx("El usuario ya esta registrado como técnico.");
        }

        if (tecnicoRepository.existsByCuit(dto.getCuit())){
            throw new DuplicateCuitEx("El cuit ya existe.");
        }

        //validar que las habilidades existan
        if (dto.getIdHabilidades().stream().anyMatch(id -> !habilidadRepository.existsById(id))) {
            throw new HabilidadNotFoundEx("Una o más habilidades no existen.");
        }

        //validar que las especialidades existan
        if (dto.getIdEspecialidades().stream().anyMatch(id -> !especialidadRepository.existsById(id))) {
            throw new EspecialidadNotFoundEx("Una o más especialidades no existen.");
        }

        TecnicoEntity tecnico=tecnicoMapper.toEntity(dto);
        tecnico.setUsuario(user);
        tecnico.setHabilidades(habilidadRepository.findAllById(dto.getIdHabilidades()));
        tecnico.setEspecialidades(especialidadRepository.findAllById(dto.getIdEspecialidades()));
        return tecnicoMapper.toResponse(tecnicoRepository.save(tecnico));
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
                        .orElseThrow(TecnicoNotFoundEx::new)
        );
    }

    //filtrar por habilidad
    public List<TecnicoDTOResponse> getTecnicosByHabilidad(Integer idHabilidad){
        return tecnicoRepository.findByHabilidades_IdHabilidad(idHabilidad)
                .stream()
                .map(tecnicoMapper::toResponse)
                .toList();
    }

    //filtrar por especialidad
    public List<TecnicoDTOResponse> getTecnicosByEspecialidad(Integer idEspecialidad){
        return tecnicoRepository.findByEspecialidades_IdEspecialidad(idEspecialidad)
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
                .orElseThrow(TecnicoNotFoundEx::new);

        // validar que las habilidades existan
        if(dto.getIdHabilidades().stream().anyMatch(id -> !habilidadRepository.existsById(id))) {
            throw new HabilidadNotFoundEx("Una o más habilidades no existen.");
        }

        // validar que las especialidades existan
        if(dto.getIdEspecialidades().stream().anyMatch(id -> !especialidadRepository.existsById(id))) {
            throw new EspecialidadNotFoundEx("Una o más especialidades no existen.");
        }

        tecnico.setCuit(dto.getCuit());
        tecnico.setDescripcionTrabajo(dto.getDescripcionTrabajo());
        tecnico.setProyectos(dto.getProyectos());
        tecnico.setHabilidades(habilidadRepository.findAllById(dto.getIdHabilidades()));
        tecnico.setEspecialidades(especialidadRepository.findAllById(dto.getIdEspecialidades()));
        return tecnicoMapper.toResponse(tecnicoRepository.save(tecnico));
    }

    //eliminar tecnico
    public void deleteTecnico(String uuid){
        TecnicoEntity tecnico=tecnicoRepository.findByUuid(uuid)
                .orElseThrow(TecnicoNotFoundEx::new);
        tecnicoRepository.delete(tecnico);
    }

    //listar tecnicos ordenados por fecha de registros del mas nuevo al mas viejo
    public List<TecnicoDTOResponse> getTecnicosOrderByFechaRegistroDesc(){
        return tecnicoRepository.findAllByOrderByUsuario_FechaRegistroDesc()
                .stream()
                .map(tecnicoMapper::toResponse)
                .toList();
    }

    //listar tecnicos ordenados por fecha de registros del mas viejo al mas nuevi
    public List<TecnicoDTOResponse> getTecnicosOrderByFechaRegistroAsc(){
        return tecnicoRepository.findAllByOrderByUsuario_FechaRegistroAsc()
                .stream()
                .map(tecnicoMapper::toResponse)
                .toList();
    }
}
