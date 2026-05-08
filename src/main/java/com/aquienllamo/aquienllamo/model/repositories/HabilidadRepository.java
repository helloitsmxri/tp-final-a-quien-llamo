package com.aquienllamo.aquienllamo.model.repositories;

import com.aquienllamo.aquienllamo.model.entities.HabilidadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HabilidadRepository extends JpaRepository<HabilidadEntity,Integer> {
    Optional<HabilidadEntity> findByUuid(String uuid);
    boolean existsByNombreHabilidad(String nombreHabilidad);
}
