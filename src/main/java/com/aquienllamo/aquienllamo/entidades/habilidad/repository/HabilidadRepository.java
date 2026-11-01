package com.aquienllamo.aquienllamo.entidades.habilidad.repository;

import com.aquienllamo.aquienllamo.entidades.habilidad.entity.HabilidadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HabilidadRepository extends JpaRepository<HabilidadEntity,Integer> {
    Optional<HabilidadEntity> findByUuid(String uuid);
    boolean existsByUuid(String uuid);
    List<HabilidadEntity> findAllByUuidIn(List<String> uuids);
    boolean existsByNombreHabilidad(String nombreHabilidad);
    List<HabilidadEntity> findByNombreHabilidadContainingIgnoreCase(String nombre);
}
