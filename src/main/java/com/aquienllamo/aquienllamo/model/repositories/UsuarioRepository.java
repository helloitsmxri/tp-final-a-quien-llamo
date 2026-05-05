package com.aquienllamo.aquienllamo.model.repositories;

import com.aquienllamo.aquienllamo.model.entities.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository // aclaramos q es repositorio
public interface UsuarioRepository extends JpaRepository<UsuarioEntity,Integer> {
    // uso optionals porque pueden devolver nullpointer exception...

    // no necesito ni save, ni update, ni delete, ni findbyid porque jpa repository ya los trae!!!
    Optional<UsuarioEntity> findByEmail (String email);
    Optional<UsuarioEntity> findByDni (String dni);

    // verifico q existen el email o el dni...
    boolean existsByEmail(String email);
    boolean existsByDni(String dni);
}
