package hua.gr.dit.config;

import hua.gr.dit.Entitties.Admin;
import hua.gr.dit.Entitties.Estate;
import hua.gr.dit.Entitties.Role;
import hua.gr.dit.Entitties.User;
import hua.gr.dit.repositories.EstateRepository;
import hua.gr.dit.repositories.RoleRepository;
import hua.gr.dit.repositories.UserRepository;
import hua.gr.dit.service.EstateService;
import hua.gr.dit.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Configuration
public class AppConfig {

    private RoleRepository roleRepository;
    private EstateRepository estateRepository;
    private EstateService estateService;
    private UserRepository userRepository;

    public AppConfig(EstateRepository estateRepository, EstateService estateService, RoleRepository roleRepository, UserRepository userRepository) {
        this.estateRepository = estateRepository;
        this.estateService = estateService;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
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

//    Άμα θέλουμε να προσθέσουμε deafault estates
//    @PostConstruct
//    @Transactional
//    public void setupEstates() {
//        List<Estate> estates = new ArrayList<>();
//
//        estates.add(new Estate( 120, "Apartment", "123 Main St", "Downtown", 2005, 12, 250000.0f, "3rd", 3, "Central Heating", true, true, "Spacious apartment with modern amenities", "2024-02-08"));
//        estates.add(new Estate(85, "Condo", "456 Oak Ave", "Suburb", 2010, 6, 180000.0f, "2nd", 2, "Gas Heating", false, true, "Cozy condo close to parks and shopping centers", "2024-02-07"));
//        estates.add(new Estate( 200, "Villa", "789 Pine Rd", "Beachfront", 2018, 24, 750000.0f, "Ground", 5, "Solar Heating", true, false, "Luxury beachfront villa with ocean view", "2024-02-06"));
//        estates.add(new Estate( 65, "Studio", "321 Maple Dr", "City Center", 2000, 12, 120000.0f, "5th", 1, "Electric Heating", false, true, "Affordable studio in the heart of the city", "2024-02-05"));
//        estates.add(new Estate( 150, "Penthouse", "555 Sunset Blvd", "Skyline", 2022, 36, 950000.0f, "10th", 4, "Geothermal Heating", true, true, "Exclusive penthouse with rooftop terrace", "2024-02-04"));
//
//        estates.forEach(estate -> {
////            if(estateService.getEstateById(estate.getId()) == null){
//                estateService.saveEstate(estate);
////            }
//        });
//    }







}
