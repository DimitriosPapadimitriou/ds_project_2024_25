package hua.gr.dit.config;

import hua.gr.dit.Entitties.Role;
import hua.gr.dit.Entitties.User;
import hua.gr.dit.repositories.RoleRepository;
import hua.gr.dit.repositories.UserRepository;
import hua.gr.dit.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

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
                        .requestMatchers("/", "/home","/register" ,"/images/**", "/js/**", "/css/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true) // pou 8a me kanei redirect meta to login
                        .permitAll())
                .logout((logout) -> logout.permitAll());
        return http.build();
    }



}

