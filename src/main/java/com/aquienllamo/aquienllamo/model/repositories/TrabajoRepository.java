package com.aquienllamo.aquienllamo.model.repositories;

import com.aquienllamo.aquienllamo.model.Enum.EstadoTrabajo;
import com.aquienllamo.aquienllamo.model.entities.TrabajoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrabajoRepository extends JpaRepository<TrabajoEntity, Integer> {

    List<TrabajoEntity> findAllByEstadoTrabajo(EstadoTrabajo estadoTrabajo);
    Optional<TrabajoEntity> findByUuid(String uuid);
    

}
