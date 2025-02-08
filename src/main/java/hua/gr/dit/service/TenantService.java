package hua.gr.dit.service;

import hua.gr.dit.Entitties.*;
import hua.gr.dit.repositories.ApplicationForViewRepository;
import hua.gr.dit.repositories.ApplicationOfRentalRepository;
import hua.gr.dit.repositories.EstateRepository;
import hua.gr.dit.repositories.TenantRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TenantService {

    private TenantRepository tenantRepository;
    private ApplicationForViewRepository viewRepository;
    private ApplicationOfRentalRepository rentalRepository;
    private EstateRepository estateRepository;
    private NotificationService notificationService;
    private UserService userService;

    public TenantService(EstateRepository estateRepository, NotificationService notificationService, ApplicationOfRentalRepository rentalRepository, TenantRepository tenantRepository, UserService userService, ApplicationForViewRepository viewRepository) {
        this.estateRepository = estateRepository;
        this.notificationService = notificationService;
        this.rentalRepository = rentalRepository;
        this.tenantRepository = tenantRepository;
        this.userService = userService;
        this.viewRepository = viewRepository;
    }

    public Tenant findTenantById(Integer id) {
        // Fetch tenant from the repository
        return tenantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No tenant found with id: " + id));
    }

    @Transactional
    public ApplicationOfRental submitRentalApplication(ApplicationOfRental application) {
        Tenant tenant = application.getTenant();
        if (tenant == null) {
            throw new RuntimeException("Tenant must be associated with the application.");
        }
        application.setTenant(tenant);

        ApplicationOfRental savedApplication = rentalRepository.save(application);

        application.setStatus("Pending");

        String tenantEmail = tenant.getUser().getEmail();
        String msg = "Your viewing application has been successfully submitted for estate #" +
                savedApplication.getEstate().getId() + ". Our team will review it shortly.";
        notificationService.sendNotification(tenantEmail, msg);;


        return savedApplication;
    }

    @Transactional
    public ApplicationForView submitViewingApplication(ApplicationForView application) {
        if (application.getTenant() == null) {
            throw new RuntimeException("Tenant must be associated with the application.");
        }

        // Fetch tenant from the database to ensure it's managed by JPA
        Tenant tenant = tenantRepository.findById(application.getTenant().getId())
                .orElseThrow(() -> new RuntimeException("Tenant not found!"));

        application.setTenant(tenant);

        // Set additional fields if necessary
        if (application.getEstate() != null) {
            Estate estate = estateRepository.findById(application.getEstate().getId())
                    .orElseThrow(() -> new RuntimeException("Estate not found!"));
            application.setEstate(estate);
        }

        application.setStatus("Pending");


        ApplicationForView savedApplication = viewRepository.save(application);

        // Send notification
        String tenantEmail = tenant.getUser().getEmail();
        String msg = "Your viewing application has been successfully submitted for estate #" +
                savedApplication.getEstate().getId() + ". Our team will review it shortly.";
        notificationService.sendNotification(tenantEmail, msg);

        return savedApplication;
    }

    @Transactional
    public List<Estate> searchEstates(String area, Integer minRooms, Float maxPrice) {
        return estateRepository.findByAreaAndAmountOfRoomsAndPrice(area, minRooms, maxPrice);
    }


}
