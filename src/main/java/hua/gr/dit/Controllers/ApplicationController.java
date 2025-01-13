package hua.gr.dit.Controllers;
//package hua.ds_project.project.controllers;

import hua.gr.dit.Entitties.ApplicationForRegistration;
import hua.gr.dit.Entitties.ApplicationForView;
import hua.gr.dit.Entitties.ApplicationOfRental;
import hua.gr.dit.Entitties.Estate;
import hua.gr.dit.service.OwnerService;
import hua.gr.dit.service.TenantService;
import jakarta.annotation.PostConstruct;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("/applications")
public class ApplicationController {

    private OwnerService ownerService;
    private TenantService tenantService;

    public ApplicationController(OwnerService ownerService, TenantService tenantService) {
        this.ownerService = ownerService;
        this.tenantService = tenantService;
    }

    ArrayList<ApplicationForRegistration> applicationForRegistrations = new ArrayList<ApplicationForRegistration>();
    ArrayList<ApplicationForView> applicationForViewList = new ArrayList<>();
    ArrayList<ApplicationOfRental> applicationForRental = new ArrayList<ApplicationOfRental>();

    @PostConstruct
    public void setup() {
//        ApplicationForRegistration registrationApplication = new ApplicationForRegistration();
//        registrationApplication.setApplicationID(1);
//        registrationApplication.setDocuments("Ownership Proof, Tax Receipts");
//        registrationApplication.setDate("2025-01-05");
//        registrationApplication.setProperties("Apartment");
//        registrationApplication.setSquareMeters(120);
//        registrationApplication.setTypeOfEstate("Residential");
//        registrationApplication.setAddress("123 Main Street, Springfield");
//        registrationApplication.setArea("Downtown");
//        registrationApplication.setAgeOfConstruction(5);
//        registrationApplication.setDuration(12);
//        registrationApplication.setPrice(1500);
//        registrationApplication.setFloor("3rd");
//        registrationApplication.setAmountOfRooms(3);
//        registrationApplication.setTypeOfHeating("Central");
//        registrationApplication.setParking(true);
//        registrationApplication.setDescription("Newly renovated apartment available for registration.");
//        applicationForRegistrations.add(registrationApplication);
//
//
//        //APPLICATIONS FOR VIEWWWWW
//        ApplicationForView viewApplication = new ApplicationForView();
//        viewApplication.setApplicationID(2);
//        viewApplication.setDate("2025-01-10");
//        viewApplication.setDescription("Request to view a property in the city center.");
//        viewApplication.setDateOfVisit("2025-01-15");
//        viewApplication.setTenantID(101);
//        viewApplication.setOwnerID(202);
//        applicationForViewList.add(viewApplication);
//
//        ApplicationForView viewApplication2 = new ApplicationForView();
//        viewApplication2.setApplicationID(3);
//        viewApplication2.setDate("2025-01-10");
//        viewApplication2.setDescription("Request to view a property in the city center.");
//        viewApplication2.setDateOfVisit("2025-01-15");
//        viewApplication2.setTenantID(101);
//        viewApplication2.setOwnerID(201);
//        applicationForViewList.add(viewApplication2);
//        ///////////////////////////////
//
//
//        ApplicationOfRental rentalApplication = new ApplicationOfRental();
//        rentalApplication.setApplicationID(3);
//        rentalApplication.setDate("2025-01-05");
//        rentalApplication.setDescription("Rental application for a 2-bedroom apartment.");
//        rentalApplication.setDuration("12 months");
//        rentalApplication.setRent(1200);
//        rentalApplication.setTenantID(102);
//        rentalApplication.setOwnerID(202);
//        applicationForRental.add(rentalApplication);
//

    }


    @GetMapping()
    public String application(){
        return "ApplicationPage";
    }

//    @Secured("ROLE_OWNER")
    @GetMapping("/registration/new")
    public String addApplicationForRegistration(Model model, Integer adminId, Integer ownerId, Estate estate){
        model.addAttribute("application", ownerService.submitApplicationForRegistration(adminId, ownerId, estate));
        return "ApplicationForRegistrationPage";
    }
    @Secured("ROLE_ADMIN")
    @GetMapping("/registration/{id}")
    public String applicationForRegistration(Model model, Integer adminId, Integer ownerId, Estate estate){
        model.addAttribute("application", ownerService.submitApplicationForRegistration(adminId, ownerId, estate));
        return "ApplicationForRegistrationPage";
    }

    @GetMapping("/rental")
    public String applicationForRental(Model model){
        ApplicationOfRental rental = new ApplicationOfRental();
        model.addAttribute("application", tenantService.submitRentalApplication(rental));
        return "ApplicationForRentalPage";
    }

    @GetMapping("/view")
    public String applicationForView(Model model){
        ApplicationForView view = new ApplicationForView();
        model.addAttribute("application", tenantService.submitViewingApplication(view));
        return "ApplicationForRentalPage";
    }



//    @GetMapping("/view")
//    public String applicationForView(Model model){
//        ArrayList<ApplicationForView> shownApplicationForView = new ArrayList<>();
//
//        for(ApplicationForView application: applicationForViewList){
//            if(application.getOwnerID() == 201){
//                shownApplicationForView.add(application);
//            }
//        }
//        model.addAttribute("applicationForView", shownApplicationForView);
//        return "ApplicationForViewPage";
//    }



}
