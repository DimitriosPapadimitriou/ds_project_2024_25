package hua.gr.dit.config;

import hua.gr.dit.Entitties.Role;
import hua.gr.dit.Entitties.User;
import hua.gr.dit.repositories.RoleRepository;
import hua.gr.dit.repositories.UserRepository;
import hua.gr.dit.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    private UserService userService;

    private UserDetailsService userDetailsService;

    private BCryptPasswordEncoder passwordEncoder;

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public SecurityConfig(BCryptPasswordEncoder passwordEncoder, RoleRepository roleRepository, UserDetailsService userDetailsService, UserRepository userRepository, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/home", "/register", "/images/**", "/js/**", "/css/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true) // pou 8a me kanei redirect meta to login
                        .permitAll())
                .logout((logout) -> logout.permitAll());
        return http.build();
    }

    @PostConstruct
    @Transactional
    public void initializeAdmins() {


        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseThrow(() -> new IllegalStateException("ROLE_ADMIN is not initialized in the database!"));


        User admin1 = new User();
        admin1.setEmail("admin1@example.com");
        admin1.setUsername("admin1");
        admin1.setPassword(passwordEncoder.encode("admin1"));
        admin1.setRoles(Set.of(adminRole));
        userRepository.save(admin1);

        User admin2 = new User();
        admin2.setEmail("admin2@example.com");
        admin2.setUsername("admin2");
        admin2.setPassword(passwordEncoder.encode("admin2"));
        admin2.setRoles(Set.of(adminRole));
        userRepository.save(admin2);

        User admin3 = new User();
        admin3.setEmail("admin3@example.com");
        admin3.setUsername("admin3");
        admin3.setPassword(passwordEncoder.encode("admin3"));
        admin3.setRoles(Set.of(adminRole));
        userRepository.save(admin3);
    }


}
