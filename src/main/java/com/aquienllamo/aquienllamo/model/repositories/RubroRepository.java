package com.aquienllamo.aquienllamo.model.repositories;

import com.aquienllamo.aquienllamo.model.entities.RubroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RubroRepository extends JpaRepository<RubroEntity, Integer> {

    //buscar un rubro segun su uuid
    Optional<RubroEntity> findByUUIDField(String uuidRubro);

    //buscar rubro por nombre
    Optional<RubroEntity> findByFieldName(String nombreRubro);

    //verificar si existe un rubro por nombre
    boolean existsByNameField(String nombreRubro);
}
