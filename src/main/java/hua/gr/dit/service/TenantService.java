package hua.gr.dit.service;

import hua.gr.dit.Entitties.ApplicationForView;
import hua.gr.dit.Entitties.ApplicationOfRental;
import hua.gr.dit.Entitties.Estate;
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

    public TenantService(TenantRepository tenantRepository, ApplicationForViewRepository viewRepository, ApplicationOfRentalRepository rentalRepository, EstateRepository estateRepository, NotificationService notificationService) {
        this.tenantRepository = tenantRepository;
        this.viewRepository = viewRepository;
        this.rentalRepository = rentalRepository;
        this.estateRepository = estateRepository;
        this.notificationService = notificationService;
    }

    @Transactional
    public ApplicationOfRental submitRentalApplication(ApplicationOfRental application) {
        if (application.getTenant() == null) {
            throw new RuntimeException("Tenant must be associated with the application.");
        }

        ApplicationOfRental savedApplication = rentalRepository.save(application);

        String tenantEmail = application.getTenant().getEmail();
        String msg = "Your rental application for estate #" + savedApplication.getEstate() +  " has been successfully submitted. Our team will review it shortly.";
        notificationService.sendNotification(tenantEmail, msg);

        return savedApplication;
    }

    @Transactional
    public ApplicationForView submitViewingApplication(ApplicationForView application) {
        if (application.getTenant() == null) {
            throw new RuntimeException("Tenant must be associated with the application.");
        }

        ApplicationForView savedApplication = viewRepository.save(application);

        String tenantEmail = application.getTenant().getEmail();
        String msg = "Your viewing application for estate #" + savedApplication.getEstate() +  " has been successfully submitted. Our team will review it shortly.";
        notificationService.sendNotification(tenantEmail, msg);

        return savedApplication;
    }

    @Transactional
    public List<Estate> searchEstates(String area, Integer minRooms, Float maxPrice) {
        return estateRepository.findByAreaAndAmountOfRoomsAndPrice(area, minRooms, maxPrice);
    }


}
