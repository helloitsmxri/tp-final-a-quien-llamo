package com.aquienllamo.aquienllamo.model.repositories;

import com.aquienllamo.aquienllamo.model.entities.PresupuestoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PresupuestoRepository extends JpaRepository<PresupuestoEntity, Integer>, JpaSpecificationExecutor<PresupuestoEntity> {
    // ya hace solo el save, delete, update, find by id!!
    // obtener por uuid el presupuesto
    Optional<PresupuestoEntity> findByUuid(String uuid);
    // ver si existe el presupuesto
    boolean existsByUuid(String uuid);
}
