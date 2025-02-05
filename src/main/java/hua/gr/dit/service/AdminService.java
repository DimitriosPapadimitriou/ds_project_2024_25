package hua.gr.dit.service;

import hua.gr.dit.Entitties.ApplicationForRegistration;
import hua.gr.dit.Entitties.User;
import hua.gr.dit.repositories.ApplicationForRegistrationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private ApplicationForRegistrationRepository registrationRepository;
    private NotificationService notificationService;

    public AdminService(ApplicationForRegistrationRepository registrationRepository, NotificationService notificationService) {
        this.registrationRepository = registrationRepository;
        this.notificationService = notificationService;
    }

    public ApplicationForRegistration acceptApplication(Integer applicationId) {
        ApplicationForRegistration application = registrationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found with ID: " + applicationId));

//        application.setStatus("Accepted");

        registrationRepository.save(application);

        User ownerUser = application.getOwner().getUser();
        if(ownerUser != null) {
            String ownerEmail = ownerUser.getEmail();
            String message = "Your application for property registration has been accepted.";
            notificationService.sendNotification(ownerEmail, message);
        } else {
            throw new RuntimeException("Associated User for Owner not found. Unable to send notification.");
        }


        return application;
    }

    public ApplicationForRegistration rejectApplication(Integer applicationId) {
        ApplicationForRegistration application = registrationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found with ID: " + applicationId));

//        application.setStatus("Rejected");

        registrationRepository.save(application);

        User ownerUser = application.getOwner().getUser();
        if(ownerUser != null) {
            String ownerEmail = ownerUser.getEmail();
            String message = "Your application for property registration has been rejected.";
            notificationService.sendNotification(ownerEmail, message);
        } else {
            throw new RuntimeException("Associated User for Owner not found. Unable to send notification.");
        }

        return application;
    }

    public List<ApplicationForRegistration> getPendingApplications() {
        return registrationRepository.findByStatus("Pending");
    }


}
