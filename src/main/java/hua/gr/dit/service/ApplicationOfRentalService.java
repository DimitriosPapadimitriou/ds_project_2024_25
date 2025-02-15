package hua.gr.dit.service;

import hua.gr.dit.Entitties.ApplicationForRegistration;
import hua.gr.dit.Entitties.ApplicationForView;
import hua.gr.dit.Entitties.ApplicationOfRental;
import hua.gr.dit.Entitties.User;
import hua.gr.dit.repositories.ApplicationForRegistrationRepository;
import hua.gr.dit.repositories.ApplicationOfRentalRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApplicationOfRentalService {

    ApplicationOfRentalRepository applicationOfRentalRepository;

    public ApplicationOfRentalService(ApplicationOfRentalRepository applicationOfRentalRepository) {
        this.applicationOfRentalRepository = applicationOfRentalRepository;
    }

    @Transactional
    public List<ApplicationOfRental> showApplicationOfRentalWithoutStatusByEstateId(Integer estateId){
        List<ApplicationOfRental> applicationsOfRental = applicationOfRentalRepository.findRentalApplicationsByEstateId(estateId);
        System.out.println("Fetched applications for rental: " + applicationsOfRental.size());  // Check the size of the list

        List<ApplicationOfRental> applicationsOfRentalWithoutStatus = new ArrayList<ApplicationOfRental>();

        for (ApplicationOfRental application : applicationsOfRental) {
            if (application.getStatus() == null || application.getStatus().trim().isEmpty() || application.getStatus().equals("Pending")) {
                System.out.println("Application ID: " + application + " Status: " + application.getStatus());
                applicationsOfRentalWithoutStatus.add(application);
            }
        }

        return applicationsOfRentalWithoutStatus;
    }
}
