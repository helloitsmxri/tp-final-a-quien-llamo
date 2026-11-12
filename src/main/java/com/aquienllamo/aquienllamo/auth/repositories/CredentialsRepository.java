package com.aquienllamo.aquienllamo.auth.repositories;


import com.aquienllamo.aquienllamo.auth.credentials.CredentialsEntity;
import com.aquienllamo.aquienllamo.auth.permissions.RolesUser;
import com.aquienllamo.aquienllamo.entidades.usuario.entities.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CredentialsRepository extends JpaRepository<CredentialsEntity, Integer> {

    Optional<CredentialsEntity> findByUsername(String username);
    Optional<CredentialsEntity> findByRefreshToken(String refreshToken);
    Optional<CredentialsEntity> findByUsuario(UsuarioEntity user);
    boolean existsByRefreshToken(String refreshToken);
    Optional<CredentialsEntity> findByUsuario_Uuid(String uuid);
    List<CredentialsEntity> findAllByRoles_Role(RolesUser role);

}