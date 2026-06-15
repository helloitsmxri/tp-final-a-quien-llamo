package com.aquienllamo.aquienllamo.model.repositories;

import com.aquienllamo.aquienllamo.model.Enum.EstadoTrabajo;
import com.aquienllamo.aquienllamo.model.entities.TrabajoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrabajoRepository extends JpaRepository<TrabajoEntity, Integer> {

    List<TrabajoEntity> findAllByEstadoTrabajo(EstadoTrabajo estadoTrabajo);

    Optional<TrabajoEntity> findByUuid(String uuid);

    boolean existsByPresupuestoUuid(String presupuestoUuid);

    @Query("SELECT trabajo FROM TrabajoEntity trabajo WHERE trabajo.presupuesto.usuario.uuid = :uuidUsuario")
    List<TrabajoEntity> findAllByUuidUsuario(@Param("uuidUsuario") String uuidUsuario);

    @Query("SELECT trabajo FROM TrabajoEntity trabajo WHERE trabajo.presupuesto.tecnico.usuario.uuid = :uuidTecnico")
    List<TrabajoEntity> findAllByUuidTecnico(@Param("uuidTecnico") String uuidTecnico);
}
