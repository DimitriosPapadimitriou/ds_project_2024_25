package hua.gr.dit.Controllers;

import hua.gr.dit.Entitties.*;
import hua.gr.dit.Entitties.User;
import hua.gr.dit.repositories.*;
import hua.gr.dit.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/applications")
public class ApplicationController {

    private OwnerService ownerService;
    private TenantService tenantService;
    private AdminService adminService;
    private ApplicationOfRentalRepository applicationOfRentalRepository;
    private ApplicationOfRentalService applicationOfRentalService;
    private ApplicationForViewRepository applicationOfViewRepository;
    private ApplicationForViewService applicationForViewService;
    private AdminRepository adminRepository;
    private UserRepository userRepository;
    private EstateRepository estateRepository;
    private ApplicationForRegistrationRepository registrationRepository;

    public ApplicationController(AdminRepository adminRepository, AdminService adminService, ApplicationForViewService applicationForViewService, ApplicationOfRentalRepository applicationOfRentalRepository, ApplicationOfRentalService applicationOfRentalService, ApplicationForViewRepository applicationOfViewRepository, EstateRepository estateRepository, OwnerService ownerService, ApplicationForRegistrationRepository registrationRepository, TenantService tenantService, UserRepository userRepository) {
        this.adminRepository = adminRepository;
        this.adminService = adminService;
        this.applicationForViewService = applicationForViewService;
        this.applicationOfRentalRepository = applicationOfRentalRepository;
        this.applicationOfRentalService = applicationOfRentalService;
        this.applicationOfViewRepository = applicationOfViewRepository;
        this.estateRepository = estateRepository;
        this.ownerService = ownerService;
        this.registrationRepository = registrationRepository;
        this.tenantService = tenantService;
        this.userRepository = userRepository;
    }

    @GetMapping()
    public String application(){
        return "ApplicationPage";
    }

    //--------------- Registration Form----------------------
    @Secured("ROLE_OWNER")
    @GetMapping("/registration/new")
    public String addApplicationForRegistration(Model model){
        model.addAttribute("application", new ApplicationForRegistration());
        return "ApplicationForRegistrationPage";
    }


    @Secured("ROLE_OWNER")
    @PostMapping("/registration/new")
    public String saveApplicationForRegistration(Model model, @ModelAttribute("ApplicationForRegistration") ApplicationForRegistration application){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        System.out.println("Application Details: " + application);

        User user = userRepository.findByEmail(email);

        if (user.getOwner() == null) {
            throw new IllegalStateException("User does not have an associated Owner entity.");
        }
        System.out.println("DEBUG: owner ID" + user.getId());

        List<Admin> admins = adminRepository.findAll();
        if (admins.isEmpty()) {
            throw new IllegalStateException("No admins are available to assign.");
        }
        Admin randomAdmin = getRandomAdmin(admins);


        ownerService.submitApplicationForRegistration(application,randomAdmin.getId(), user.getId());
        System.out.println("DEBUG: Application saved. ID: " + application.getApplicationID());


        return "ApplicationForRegistrationPage";
    }

    private Admin getRandomAdmin(List<Admin> admins) {
        Random random = new Random();
        int index = random.nextInt(admins.size());
        return admins.get(index);
    }


    @Secured("ROLE_TENANT")
    @GetMapping("/rental/new/{estateID}")
    public String applicationOfRental(Model model, @PathVariable("estateID") Integer estateID){
        Estate estate = estateRepository.findById(estateID)
                .orElseThrow(() -> new RuntimeException("Estate not found"));
        model.addAttribute("application", new ApplicationOfRental());
        model.addAttribute("estate", estate);
        System.out.println("DEBUG: ESTATE:" + estate);

        return "ApplicationForRentalPage";
    }


    @Secured("ROLE_TENANT")
    @PostMapping("/rental/new/{estateID}") // /rental/new/{estateId}
    public String handleApplicationOfRental(Model model,
                                            @PathVariable("estateID") Integer estateID,
                                            @ModelAttribute("application") ApplicationOfRental application) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        System.out.println("Application Details: " + application);


        User user = userRepository.findByEmail(email);

        if (user.getTenant() == null) {
            throw new IllegalStateException("User does not have an associated Tenant entity.");
        }

        System.out.println("DEBUG: tenant ID" + user.getId());

        application.setTenant(user.getTenant());
        application.setEstate(estateRepository.findById(estateID)
                .orElseThrow(() -> new RuntimeException("Estate not found")));


        tenantService.submitRentalApplication(application);
        model.addAttribute("successMessage", "Application submitted successfully!");

        return "redirect:/"; // Redirect to a success page
    }

//----------------- View Application Form -------------------------

    @Secured("ROLE_TENANT")
    @GetMapping("/view/new/{estateID}")
    public String applicationForView(@PathVariable("estateID") Integer estateID, Model model){
        Estate estate = estateRepository.findById(estateID)
                .orElseThrow(() -> new RuntimeException("Estate not found"));
        model.addAttribute("application", new ApplicationForView());
        model.addAttribute("estate", estate);
        System.out.println("DEBUG: ESTATE:" + estate);
        return "ApplicationForView";
    }


    @Secured("ROLE_TENANT")
    @PostMapping("/view/new/{estateID}")
    public String handleApplicationForView( Model model,
                                            @ModelAttribute("application") ApplicationForView application,
                                            @PathVariable("estateID") Integer estateID) {

        System.out.println("POST /view/new/" + estateID);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        System.out.println("Application Details: " + application);
        System.out.println("Application Details: " + application);


        User user = userRepository.findByEmail(email);

        if (user.getTenant() == null) {
            throw new IllegalStateException("User does not have an associated Tenant entity.");
        }

        application.setTenant(user.getTenant());
        application.setEstate(estateRepository.findById(estateID)
                .orElseThrow(() -> new RuntimeException("Estate not found")));


        tenantService.submitViewingApplication(application);

        model.addAttribute("successMessage", "Application submitted successfully!");

        return "redirect:/";

    }
//------------------------------------------------------------------------

    // My estates applications
    @Secured("ROLE_OWNER")
    @GetMapping("/myEstate/{estateID}")
    public String myEstateApplications(@PathVariable("estateID") Integer estateID, Model model, Principal principal){

        model.addAttribute("applicationForRental", applicationOfRentalService.showApplicationOfRentalWithoutStatusByEstateId(estateID));
        model.addAttribute("applicationOfView", applicationForViewService.showApplicationForViewWithoutStatusByEstateId(estateID));
        return "ApplicationPage";
    }

//-----------------------------------------------------------------------


    @Secured("ROLE_ADMIN")
    @PostMapping("/applications/register/{applicationId}/accept")
    public ResponseEntity<String> acceptRegistration(@PathVariable Integer applicationId){
        ApplicationForRegistration application = adminService.acceptApplication(applicationId);
        return ResponseEntity.ok("Application with ID " + applicationId + " has been accepted.");
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/applications/register/{applicationId}/reject")
    public ResponseEntity<String> rejectRegistration(@PathVariable Integer applicationId) {

        ApplicationForRegistration application = adminService.rejectApplication(applicationId);
        return ResponseEntity.ok("Application with ID " + applicationId + " has been rejected.");
    }

    @Secured("ROLE_OWNER")
    @PostMapping("/rental/reject/{applicationId}")
    public ResponseEntity<String> rejectRental(@PathVariable Integer applicationId){
        ApplicationOfRental application = ownerService.rejectApplicationOfRental(applicationId);
        return ResponseEntity.ok("Rental application with ID " + applicationId + " has been rejected.");
    }

    @Secured("ROLE_OWNER")
    @PostMapping("/rental/accept/{applicationId}")
    public ResponseEntity<String> acceptRental(@PathVariable Integer applicationId){
        ApplicationOfRental application = ownerService.acceptApplicationOfRental(applicationId);
        return ResponseEntity.ok("Rental application with ID " + applicationId + " has been accepted.");
    }

    //    @Secured("ROLE_OWNER")
    @PostMapping("/view/accept/{applicationId}")
    public String acceptViewing(@PathVariable Integer applicationId){
        ApplicationForView application = ownerService.acceptApplicationForView(applicationId);
        return "redirect:/";
    }

    @Secured("ROLE_OWNER")
    @PostMapping("/view/reject/{applicationId}")
    public ResponseEntity<String> rejectViewing(@PathVariable Integer applicationId){
        ApplicationForView application = ownerService.rejectApplicationForView(applicationId);
        return ResponseEntity.ok("Viewing application with ID " + applicationId + " has been rejected.");
    }

    @Secured("ROLE_OWNER")
    @PostMapping("applications/rental/{ownerId}")
    public String showRentalApplications(Model model, @PathVariable Integer ownerId){
        List<ApplicationOfRental> rentals = ownerService.getApplicationsOfRental(ownerId);
        model.addAttribute("rentals", rentals);
        return "ApplicationForRentalPage";
    }

    @Secured("ROLE_OWNER")
    @PostMapping("applications/view/{ownerId}")
    public String showViewingApplications(Model model, @PathVariable Integer ownerId){
        List<ApplicationForView> rentals = ownerService.getApplicationsForViewing(ownerId);
        model.addAttribute("rentals", rentals);
        return "ApplicationForViewPage";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/registrations")
    public String showRegistrations(Model model){
        List<ApplicationForRegistration> registrations = registrationRepository.findAll();
        System.out.println("test registrations value " +registrations);
        model.addAttribute("registrations", registrations);
        return "showRegistrations";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/registrations/accept/{applicationId}")
    public String acceptApplication(@PathVariable Integer applicationId) {
        adminService.acceptApplication(applicationId);
        return "redirect:/applications";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/registrations/reject/{applicationId}")
    public String rejectApplication(@PathVariable Integer applicationId) {
        adminService.rejectApplication(applicationId);
        return "redirect:/applications";
    }


}









