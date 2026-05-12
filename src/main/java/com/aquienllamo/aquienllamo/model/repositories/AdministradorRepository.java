package com.aquienllamo.aquienllamo.model.repositories;

import com.aquienllamo.aquienllamo.model.entities.AdministradorEntity;
import com.aquienllamo.aquienllamo.model.entities.UbicacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdministradorRepository extends JpaRepository<AdministradorEntity,Integer> {

    // el UUID es único, por eso debo usar Optional.
    Optional<AdministradorEntity> findByUuid(String uuid);

    Optional<AdministradorEntity> findByNombreUsuario(String nombreUsuario);
   //utilizo solo Optional porque los campos no son repetibles.

    boolean existsByUuid(String uuid);
    boolean existsByNombreUsuario(String nombreUsuario);


}
