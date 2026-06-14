package com.aquienllamo.aquienllamo.model.auth.repositories;

import com.aquienllamo.aquienllamo.model.auth.permissions.RoleEntity;
import com.aquienllamo.aquienllamo.model.auth.permissions.RolesUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
    Optional<RoleEntity> findByRole(RolesUser role);
}
