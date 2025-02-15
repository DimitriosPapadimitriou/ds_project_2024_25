package hua.gr.dit.service;


import hua.gr.dit.Entitties.ApplicationForRegistration;
import hua.gr.dit.Entitties.ApplicationForView;
import hua.gr.dit.Entitties.User;
import hua.gr.dit.repositories.ApplicationForRegistrationRepository;
import hua.gr.dit.repositories.ApplicationForViewRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApplicationForViewService {

    private ApplicationForViewRepository applicationForViewRepository;

    public ApplicationForViewService(ApplicationForViewRepository applicationForViewRepository) {
        this.applicationForViewRepository = applicationForViewRepository;
    }

    @Transactional
    public List<ApplicationForView> showApplicationForViewWithoutStatusByEstateId(Integer estateId) {
        List<ApplicationForView> applicationsForView = applicationForViewRepository.findViewApplicationsByEstateId(estateId);
        System.out.println("Fetched applications: " + applicationsForView.size());  // Check the size of the list

        List<ApplicationForView> applicationsForViewWithoutStatus = new ArrayList<>();

        for (ApplicationForView application : applicationsForView) {
            if (application.getStatus() == null || application.getStatus().trim().isEmpty() || application.getStatus().equals("Pending")) {  //  Null-safe check
                System.out.println("Application ID: " + application + " Status: " + application.getStatus());
                applicationsForViewWithoutStatus.add(application);
            }
        }
        System.out.println("the value of applicationsForViewWithoutStatus is " + applicationsForViewWithoutStatus);

        return applicationsForViewWithoutStatus;
    }
}
