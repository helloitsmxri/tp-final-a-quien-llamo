package com.aquienllamo.aquienllamo.entidades.trabajo.repository;

import com.aquienllamo.aquienllamo.entidades.trabajo.entity.TrabajoEntity;
import com.aquienllamo.aquienllamo.entidades.trabajo.enums.EstadoTrabajo;
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

    @Query("""
            SELECT trabajo FROM TrabajoEntity trabajo
            WHERE trabajo.presupuesto.usuario.uuid = :uuidUsuario
            """)
    List<TrabajoEntity> findAllByUuidUsuario(
            @Param("uuidUsuario") String uuidUsuario
    );

    @Query("""
            SELECT trabajo FROM TrabajoEntity trabajo
            WHERE trabajo.presupuesto.tecnico.usuario.uuid = :uuidTecnico
            """)
    List<TrabajoEntity> findAllByUuidTecnico(
            @Param("uuidTecnico") String uuidTecnico
    );

    @Query("""
            SELECT t FROM TrabajoEntity t
            WHERE t.presupuesto.usuario.email = :email
            OR t.presupuesto.tecnico.usuario.email = :email
            ORDER BY t.fechaEstimadaInicio DESC
            """)
    List<TrabajoEntity> findAllByParticipante(
            @Param("email") String email
    );

    @Query("""
            SELECT COUNT(t) > 0 FROM TrabajoEntity t
            WHERE t.estadoTrabajo = :estado
            AND (
                (t.presupuesto.usuario.uuid = :uuidUsuario1
                AND t.presupuesto.tecnico.usuario.uuid = :uuidUsuario2)
                OR
                (t.presupuesto.usuario.uuid = :uuidUsuario2
                AND t.presupuesto.tecnico.usuario.uuid = :uuidUsuario1)
            )
            """)
    boolean existsTrabajoFinalizadoEntreUsuarios(
            @Param("uuidUsuario1") String uuidUsuario1,
            @Param("uuidUsuario2") String uuidUsuario2,
            @Param("estado") EstadoTrabajo estado
    );
}