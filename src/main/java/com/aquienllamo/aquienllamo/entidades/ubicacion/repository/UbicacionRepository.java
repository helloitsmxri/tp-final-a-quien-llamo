package com.aquienllamo.aquienllamo.entidades.ubicacion.repository;

import com.aquienllamo.aquienllamo.entidades.ubicacion.entity.UbicacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UbicacionRepository extends JpaRepository<UbicacionEntity, Integer> {



    List<UbicacionEntity> findByUsuario_IdUsuario(Integer idUsuario);

    Optional<UbicacionEntity>findByUuid(String uuid);
    
}
