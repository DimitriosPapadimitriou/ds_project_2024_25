package hua.gr.dit.service;

import hua.gr.dit.Entitties.ApplicationForRegistration;
import hua.gr.dit.Entitties.Estate;
import hua.gr.dit.Entitties.User;
import hua.gr.dit.repositories.ApplicationForRegistrationRepository;
import hua.gr.dit.repositories.EstateRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AdminService {

    private final EstateRepository estateRepository;
    private ApplicationForRegistrationRepository registrationRepository;

    public AdminService(ApplicationForRegistrationRepository registrationRepository, EstateRepository estateRepository) {
        this.registrationRepository = registrationRepository;
        this.estateRepository = estateRepository;
    }

    public ApplicationForRegistration acceptApplication(Integer applicationId) {
        ApplicationForRegistration application = registrationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found with ID: " + applicationId));

        application.setStatus("Accepted");
        Estate estate = new Estate();
        estate.setOwner(application.getOwner());
        estate.setSquareMeters(application.getSquareMeters());
        estate.setTypeOfEstate(application.getTypeOfEstate());
        estate.setAddress(application.getAddress());
        estate.setArea(application.getArea());
        estate.setAgeOfConstruction(application.getAgeOfConstruction());
        estate.setDuration(application.getDuration());
        estate.setPrice(application.getPrice());
        estate.setFloor(application.getFloor());
        estate.setAmountOfRooms(application.getAmountOfRooms());
        estate.setTypeOfHeating(application.getTypeOfHeating());
        estate.setParking(application.getParking());
        estate.setAvailability(Boolean.TRUE);
        estate.setDescription(application.getDescription());
        estate.setLastUpdated(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        estateRepository.save(estate);



        return registrationRepository.save(application);
    }

    public ApplicationForRegistration rejectApplication(Integer applicationId) {
        ApplicationForRegistration application = registrationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found with ID: " + applicationId));

        application.setStatus("Rejected");


        return registrationRepository.save(application);
    }

    public List<ApplicationForRegistration> getPendingApplications() {
        return registrationRepository.findByStatus("Pending");
    }




}
