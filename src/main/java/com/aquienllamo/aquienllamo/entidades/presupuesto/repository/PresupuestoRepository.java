package com.aquienllamo.aquienllamo.entidades.presupuesto.repository;

import com.aquienllamo.aquienllamo.entidades.presupuesto.enums.EstadoPresupuestoE;
import com.aquienllamo.aquienllamo.entidades.presupuesto.entity.PresupuestoEntity;
import com.aquienllamo.aquienllamo.entidades.tecnico.entity.TecnicoEntity;
import com.aquienllamo.aquienllamo.entidades.usuario.entities.UsuarioEntity;
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
