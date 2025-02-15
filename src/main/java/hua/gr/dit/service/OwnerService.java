package hua.gr.dit.service;

import hua.gr.dit.Application;
import hua.gr.dit.Entitties.*;
import hua.gr.dit.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class OwnerService {

    private OwnerRepository ownerRepository;
    private EstateRepository estateRepository;
    private ApplicationForViewRepository viewRepository;
    private ApplicationOfRentalRepository rentalRepository;
    private ApplicationForRegistrationRepository registrationRepository;
    private AdminRepository adminRepository;

    @Autowired
    public OwnerService(AdminRepository adminRepository, EstateRepository estateRepository, OwnerRepository ownerRepository, ApplicationForRegistrationRepository registrationRepository, ApplicationOfRentalRepository rentalRepository, ApplicationForViewRepository viewRepository) {
        this.adminRepository = adminRepository;
        this.estateRepository = estateRepository;

        this.ownerRepository = ownerRepository;
        this.registrationRepository = registrationRepository;
        this.rentalRepository = rentalRepository;
        this.viewRepository = viewRepository;
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

    public void saveApplication(ApplicationForRegistration application) {

        System.out.println("DEBUG: Saving application to database...");
        registrationRepository.save(application);
        System.out.println("DEBUG: Application saved successfully.");

    }


    @Transactional
    public ApplicationForRegistration submitApplicationForRegistration( ApplicationForRegistration application, @RequestParam("adminId") Integer adminId, @RequestParam("ownerId") Integer ownerId){ // Estate estate
        Owner owner = ownerRepository.findById(ownerId).orElseThrow( () -> new RuntimeException("Owner with the following ID not found:" + ownerId));
        Admin admin = adminRepository.findById(adminId).orElseThrow( () -> new RuntimeException("Admin with the following ID not found:" + adminId));


        application.setOwner(owner);
        application.setStatus("Pending");

        System.out.println("DEBUG: Saving application to database...");
        ApplicationForRegistration savedApplication = registrationRepository.save(application);
        System.out.println("DEBUG: Application saved with ID: " + savedApplication.getApplicationID());

        return  application;
    }

    @Transactional
    public ApplicationOfRental acceptApplicationOfRental(Integer applicationId){
        ApplicationOfRental acceptedApplication = rentalRepository.findById(applicationId).orElseThrow(() -> new RuntimeException("Application not found with ID: " + applicationId));
        acceptedApplication.setStatus("Accepted");
        Estate estate = acceptedApplication.getEstate();
        estate.setAvailability(Boolean.FALSE);

        List<ApplicationOfRental> otherApplicationsRental = rentalRepository.findByEstateAndStatus(estate, "Pending");
        for (ApplicationOfRental application : otherApplicationsRental) {
            application.setStatus("Rejected");
            rentalRepository.save(application);
        }

        List<ApplicationForView> otherApplicationsView = viewRepository.findByEstateAndStatus(estate, "Pending");
        for (ApplicationForView application : otherApplicationsView) {
            application.setStatus("Rejected");
            viewRepository.save(application);
        }

        return rentalRepository.save(acceptedApplication);
    }

    @Transactional
    public ApplicationOfRental rejectApplicationOfRental(Integer applicationId){
        ApplicationOfRental application = rentalRepository.findById(applicationId).orElseThrow(() -> new RuntimeException("Application not found with ID: " + applicationId));
        application.setStatus("Rejected");

        return rentalRepository.save(application);
    }

    @Transactional
    public ApplicationForView acceptApplicationForView(Integer applicationId){
        ApplicationForView application = viewRepository.findById(applicationId).orElseThrow(() -> new RuntimeException("Application not found with ID: " + applicationId));
        application.setStatus("Accepted");

        return viewRepository.save(application);
    }

    @Transactional
    public ApplicationForView rejectApplicationForView(Integer applicationId){
        ApplicationForView application = viewRepository.findById(applicationId).orElseThrow(() -> new RuntimeException("Application not found with ID: " + applicationId));
        application.setStatus("Rejected");

        return viewRepository.save(application);
    }

    @Transactional
    public List<ApplicationOfRental> getApplicationsOfRental(Integer ownerId) {
        return rentalRepository.findRentalApplicationsByOwnerId(ownerId);
    }

    @Transactional
    public List<ApplicationForView> getApplicationsForViewing(Integer ownerId) {
        return rentalRepository.findViewingApplicationsByOwnerId(ownerId);
    }




}
