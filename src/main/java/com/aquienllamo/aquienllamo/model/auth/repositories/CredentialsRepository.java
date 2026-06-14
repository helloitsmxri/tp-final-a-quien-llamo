package com.aquienllamo.aquienllamo.model.auth.repositories;

import com.aquienllamo.aquienllamo.model.auth.Credentials.CredentialsEntity;
import com.aquienllamo.aquienllamo.model.entities.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CredentialsRepository extends JpaRepository<CredentialsEntity, Long> {

    Optional<CredentialsEntity> findByUsername(String username);
    Optional<CredentialsEntity> findByRefreshToken(String refreshToken);
    Optional<CredentialsEntity> findByUsuario(UsuarioEntity user);
    boolean existsByRefreshToken(String refreshToken);

}