package com.aquienllamo.aquienllamo.model.auth.credentials;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CredentialsRepository extends JpaRepository<CredentialsEntity, Integer> {
    Optional<CredentialsEntity> findByUsername(String nombre);
}
