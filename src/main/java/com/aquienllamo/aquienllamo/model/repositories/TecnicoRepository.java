package com.aquienllamo.aquienllamo.model.repositories;

import com.aquienllamo.aquienllamo.model.entities.TecnicoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TecnicoRepository extends JpaRepository<TecnicoEntity,Integer>, JpaSpecificationExecutor<TecnicoEntity> {
    Optional<TecnicoEntity> findByUuid(String uuid);
    Optional<TecnicoEntity> findByCuit(String cuit);
    boolean existsByCuit(String cuit);
    boolean existsByUsuario_idUsuario(Integer idUsuario);
    List<TecnicoEntity> findByHabilidades_IdHabilidad(Integer idHabilidad);
    List<TecnicoEntity> findByEspecialidades_IdEspecialidad(Integer idEspecialidad);
    List<TecnicoEntity> findAllByOrderByUsuario_FechaRegistroDesc();
    List<TecnicoEntity> findAllByOrderByUsuario_FechaRegistroAsc();
}
