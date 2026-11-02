package com.aquienllamo.aquienllamo.entidades.presupuesto.repository;

import com.aquienllamo.aquienllamo.entidades.presupuesto.enums.EstadoPresupuestoE;
import com.aquienllamo.aquienllamo.entidades.presupuesto.entity.PresupuestoEntity;
import com.aquienllamo.aquienllamo.entidades.tecnico.entity.TecnicoEntity;
import com.aquienllamo.aquienllamo.entidades.usuario.entities.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PresupuestoRepository extends JpaRepository<PresupuestoEntity, Integer>, JpaSpecificationExecutor<PresupuestoEntity> {
    // ya hace solo el save, delete, update, find by id!!
    // Obtener por UUID
    Optional<PresupuestoEntity> findByUuid(String uuid);

    // Obtener presupuestos de un usuario
    List<PresupuestoEntity> findByUsuario(UsuarioEntity user);

    // Obtener presupuestos de un técnico
    List<PresupuestoEntity> findByTecnico(TecnicoEntity tech);

    // Obtener presupuestos de un técnico según estado
    List<PresupuestoEntity> findByTecnicoAndEstado(TecnicoEntity tech, EstadoPresupuestoE estado);

    // Obtener presupuestos de un usuario según estado
    List<PresupuestoEntity> findByUsuarioAndEstado(UsuarioEntity user, EstadoPresupuestoE estado);

    // Obtener presupuestos de un chat
    // esto se puede hacer xq spring data jpa permite recorrer la relación
    // haciendo presupuesto entity, private chat entity, y dentro de
    // chat entity va string uuidchat

    List<PresupuestoEntity> findByChat_UuidChat(String uuidChat);

    // Ver si existe
    boolean existsByUuid(String uuid);
}
