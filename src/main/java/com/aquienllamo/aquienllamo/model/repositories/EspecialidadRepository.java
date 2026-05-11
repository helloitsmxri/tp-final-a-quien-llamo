package com.aquienllamo.aquienllamo.model.repositories;

import com.aquienllamo.aquienllamo.model.entities.EspecialidadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EspecialidadRepository extends JpaRepository<EspecialidadEntity, Integer> {
    Optional<EspecialidadEntity> findByUuid (String uuid);
    boolean existsByNombreEspecialidad(String nombreEsoecialidad);
}
