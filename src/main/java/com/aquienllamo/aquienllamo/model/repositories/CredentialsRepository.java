package com.aquienllamo.aquienllamo.model.repositories;

import com.aquienllamo.aquienllamo.model.auth.Credentials.CredentialsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CredentialsRepository extends JpaRepository<CredentialsEntity, Long> {

    Optional<CredentialsEntity> findByUsername(String username);

}