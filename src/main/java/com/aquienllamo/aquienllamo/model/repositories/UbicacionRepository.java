package com.aquienllamo.aquienllamo.model.repositories;

import com.aquienllamo.aquienllamo.model.entities.UbicacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UbicacionRepository extends JpaRepository<UbicacionEntity, Integer> {



    List<UbicacionEntity> findByUsuario_IdUsuario(Integer idUsuario);

    Optional<UbicacionEntity>findByUuid(String uuid);
    
}
