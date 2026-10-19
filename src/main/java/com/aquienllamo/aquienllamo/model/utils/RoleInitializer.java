package com.aquienllamo.aquienllamo.model.utils;

import com.aquienllamo.aquienllamo.model.auth.permissions.RoleEntity;
import com.aquienllamo.aquienllamo.model.auth.permissions.RolesUser;
import com.aquienllamo.aquienllamo.model.auth.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleInitializer implements CommandLineRunner {
    private final RoleRepository roleRepository;

    public RoleInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {

        for (RolesUser role : RolesUser.values()) {

            if (!roleRepository.existsByRole(role)) {
                RoleEntity roleEntity = new RoleEntity();
                roleEntity.setRole(role);

                roleRepository.save(roleEntity);
            }
        }
    }
}
