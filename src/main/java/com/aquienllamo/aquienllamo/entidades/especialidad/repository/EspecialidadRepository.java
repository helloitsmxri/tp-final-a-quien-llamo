package com.aquienllamo.aquienllamo.entidades.especialidad.repository;

import com.aquienllamo.aquienllamo.administrador.TipoValidacion;
import com.aquienllamo.aquienllamo.entidades.especialidad.entity.EspecialidadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EspecialidadRepository extends JpaRepository<EspecialidadEntity, Integer> {
    Optional<EspecialidadEntity> findByUuid (String uuid);
    boolean existsByNombreEspecialidad(String nombreEsoecialidad);
    List<EspecialidadEntity> findByTipoValidacion(TipoValidacion tipoValidacion);
    List<EspecialidadEntity> findByNombreEspecialidadContainingIgnoreCase(String nombre);
    boolean existsByUuid(String uuid);
    List<EspecialidadEntity> findAllByUuidIn(List<String> uuids);
}
