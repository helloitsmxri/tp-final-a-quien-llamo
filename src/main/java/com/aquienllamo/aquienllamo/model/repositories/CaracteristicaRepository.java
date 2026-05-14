package com.aquienllamo.aquienllamo.model.repositories;

import com.aquienllamo.aquienllamo.model.entities.CaracteristicaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface CaracteristicaRepository extends JpaRepository<CaracteristicaEntity, Integer>, JpaSpecificationExecutor<CaracteristicaEntity> {

    // uso optionals porque pueden devolver nullpointer exception...
    // no necesito ni save, ni update, ni delete, ni findbyid porque jpa repository ya los trae!!!
    Optional<CaracteristicaEntity> findByUuid(String uuid);

    // verificar q existe la característica:
    boolean existsByUuid(String uuid);

    String uuid(String uuid);
    boolean existsByValorAdjetivo(String valorAdjetivo);
}
