package hua.gr.dit.config;

import hua.gr.dit.Entitties.Role;
import hua.gr.dit.Entitties.User;
import hua.gr.dit.repositories.RoleRepository;
import hua.gr.dit.repositories.UserRepository;
import hua.gr.dit.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.Set;

@Configuration
public class AppConfig {

    private RoleRepository roleRepository;

    public AppConfig(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @PostConstruct
    public void setup() {
        Role role_owner = new Role("ROLE_OWNER");
        Role role_tenant = new Role("ROLE_TENANT");
        Role role_admin = new Role("ROLE_ADMIN");


        roleRepository.updateOrInsert(role_owner);
        roleRepository.updateOrInsert(role_tenant);
        roleRepository.updateOrInsert(role_admin);
    }




}
