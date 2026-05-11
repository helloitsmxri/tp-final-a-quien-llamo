package com.aquienllamo.aquienllamo.model.repositories;

import com.aquienllamo.aquienllamo.model.Enum.TipoValidacion;
import com.aquienllamo.aquienllamo.model.entities.EspecialidadEntity;
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
}
