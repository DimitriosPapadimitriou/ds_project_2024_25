package hua.gr.dit.service;

import hua.gr.dit.Entitties.*;
import hua.gr.dit.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerService {

    private OwnerRepository ownerRepository;
    private EstateRepository estateRepository;
    private ApplicationForViewRepository viewRepository;
    private ApplicationOfRentalRepository rentalRepository;
    private ApplicationForRegistrationRepository registrationRepository;

    private NotificationService notificationService;
    private AdminRepository adminRepository;

    public OwnerService(OwnerRepository ownerRepository, EstateRepository estateRepository, ApplicationForViewRepository viewRepository, ApplicationOfRentalRepository rentalRepository, ApplicationForRegistrationRepository registrationRepository, NotificationService notificationService, AdminRepository adminRepository) {
        this.ownerRepository = ownerRepository;
        this.estateRepository = estateRepository;
        this.viewRepository = viewRepository;
        this.rentalRepository = rentalRepository;
        this.registrationRepository = registrationRepository;
        this.notificationService = notificationService;
        this.adminRepository = adminRepository;
    }

    @Transactional
    public Owner registerOwner(Owner owner){
        return ownerRepository.save(owner);
    }

    @Transactional
    public Owner getOwnerById(Integer id){
        return ownerRepository.findById(id).orElse(null);
    }

    @Transactional
    public Owner updateOwner(Integer id, Owner updatedOwner) {
        Owner owner = getOwnerById(id);
        owner.setIBAN(updatedOwner.getIBAN());
        owner.setAvailableEstates(updatedOwner.getAvailableEstates());
        return ownerRepository.save(owner);
    }

    @Transactional
    public ApplicationForRegistration submitApplicationForRegistration(Integer adminId, Integer ownerId, Estate estate){
        Owner owner = ownerRepository.findById(ownerId).orElseThrow( () -> new RuntimeException("Owner with the following ID not found:" + ownerId));
        Admin admin = adminRepository.findById(adminId).orElseThrow( () -> new RuntimeException("Admin with the following ID not found:" + adminId));

        ApplicationForRegistration application = new ApplicationForRegistration();

        application.setOwner(owner);
        application.setEstate(estate);
        application.setStatus("Pending");

        registrationRepository.save(application);

        String ownerMessage = "Your application for property registration has been submitted.";
        notificationService.sendNotification(owner.getEmail(), ownerMessage);

        String adminMessage = "A new estate registration request has been received";
        notificationService.sendNotification(admin.getEmail(), adminMessage);

        return  application;
    }

    @Transactional
    public ApplicationOfRental acceptApplicationOfRental(Integer applicationId){
        ApplicationOfRental application = rentalRepository.findById(applicationId).orElseThrow(() -> new RuntimeException("Application not found with ID: " + applicationId));
        application.setStatus("Accepted");

        return rentalRepository.save(application);
    }

    @Transactional
    public ApplicationForView acceptApplicationForView(Integer applicationId){
        ApplicationForView application = viewRepository.findById(applicationId).orElseThrow(() -> new RuntimeException("Application not found with ID: " + applicationId));
        application.setStatus("Accepted");

        return viewRepository.save(application);
    }

    @Transactional
    public List<ApplicationOfRental> showApplicationsOfRental() {
        return rentalRepository.findAll();
    }




}
