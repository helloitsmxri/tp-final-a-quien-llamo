package com.aquienllamo.aquienllamo.model.repositories;

import com.aquienllamo.aquienllamo.model.Enum.EstadoPresupuestoE;
import com.aquienllamo.aquienllamo.model.entities.PresupuestoEntity;
import com.aquienllamo.aquienllamo.model.entities.TecnicoEntity;
import com.aquienllamo.aquienllamo.model.entities.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PresupuestoRepository extends JpaRepository<PresupuestoEntity, Integer>, JpaSpecificationExecutor<PresupuestoEntity> {
    // ya hace solo el save, delete, update, find by id!!
    // obtener por uuid el presupuesto
    Optional<PresupuestoEntity> findByUuid(String uuid);
    Optional<PresupuestoEntity> findByUsuario(UsuarioEntity user);
    Optional<PresupuestoEntity> findByTecnico(TecnicoEntity tech);
    Optional<PresupuestoEntity> findByTecnicoAndEstado(TecnicoEntity tech, EstadoPresupuestoE estado);
    Optional<PresupuestoEntity> findByUsuarioAndEstado(UsuarioEntity user, EstadoPresupuestoE estado);
    // ver si existe el presupuesto
    boolean existsByUuid(String uuid);
}
