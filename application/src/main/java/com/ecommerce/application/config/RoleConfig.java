package com.ecommerce.application.config;

import com.ecommerce.application.enums.RoleName;
import com.ecommerce.application.model.Role;
import com.ecommerce.application.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RoleConfig {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleConfig(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Bean
    public CommandLineRunner initRoles() {
        return args -> {
            if(roleRepository.count() == 0) {
                Role adminRole = new Role(RoleName.ROLE_ADMIN);
                Role customerRole = new Role(RoleName.ROLE_CUSTOMER);
                Role storeOwnerRole = new Role(RoleName.ROLE_STOREOWNER);

                roleRepository.saveAll(List.of(adminRole, customerRole, storeOwnerRole));

                System.out.println("Default roles have been embedded into the database.");
            }
        };
    }
}
