package hua.gr.dit.Controllers;

import hua.gr.dit.Entitties.*;
import hua.gr.dit.Entitties.User;
import hua.gr.dit.repositories.UserRepository;
import hua.gr.dit.service.AdminService;
import hua.gr.dit.service.OwnerService;
import hua.gr.dit.service.TenantService;
import hua.gr.dit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/applications")
public class ApplicationController {

    private OwnerService ownerService;
    private TenantService tenantService;
    private AdminService adminService;
    private UserService userService;
    private UserRepository userRepository;

    @Autowired
    public ApplicationController(AdminService adminService, OwnerService ownerService, TenantService tenantService, UserRepository userRepository, UserService userService) {
        this.adminService = adminService;
        this.ownerService = ownerService;
        this.tenantService = tenantService;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping()
    public String application(){
        return "ApplicationPage";
    }

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
        String username = authentication.getName();

        System.out.println("DEBUG: saveApplicationForRegistration method called.");
        System.out.println("Application Details: " + application);


        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        Integer ownerId = user.getOwner().getId();

        Integer adminId = 1;

        System.out.println("DEBUG: Calling ownerService.saveApplication()...");

        ownerService.saveApplication(application);

        System.out.println("DEBUG: Application saved. ID: " + application.getApplicationID());


//        ownerService.submitApplicationForRegistration(adminId, ownerId);



        return "ApplicationForRegistrationPage";
    }


    @Secured("ROLE_OWNER")
    @GetMapping("/registration/{id}")
    public String applicationForRegistration(Model model, Integer adminId, Integer ownerId, Estate estate){
        model.addAttribute("application", ownerService.submitApplicationForRegistration(adminId, ownerId));
        return "ApplicationForRegistrationPage";
    }

    @Secured("ROLE_TENANT")
    @GetMapping("/rental")
    public String applicationForRental(Model model){
        ApplicationOfRental rental = new ApplicationOfRental();

        model.addAttribute("application", tenantService.submitRentalApplication(rental));
        return "ApplicationForRentalPage";
    }

    @Secured("ROLE_TENANT")
    @GetMapping("/view/new")
    public String applicationForView(Model model){
        ApplicationForView view = new ApplicationForView();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Retrieve the username from the principal

        // Fetch the tenant using the username
        Tenant tenant = userService.getTenantByUsername(username);

        view.setTenant(tenant);
        model.addAttribute("application", tenantService.submitViewingApplication(view));
        return "ApplicationForViewPage";
    }

    @Secured("ROLE_TENANT")
    @PostMapping("/view/new")
    public String handleApplicationForView(@ModelAttribute("application") ApplicationForView applicationForView, Model model) {
        tenantService.submitViewingApplication(applicationForView);
        model.addAttribute("successMessage", "Application submitted successfully!");

        // Redirect or return a success page
        return "redirect:/success"; // Redirect to a success page
    }

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
    @PostMapping("/applications/rental/{applicationId}/accept")
    public ResponseEntity<String> acceptRental(@PathVariable Integer applicationId){
        ApplicationOfRental application = ownerService.acceptApplicationOfRental(applicationId);
        return ResponseEntity.ok("Rental application with ID " + applicationId + " has been accepted.");
    }

    @Secured("ROLE_OWNER")
    @PostMapping("/applications/rental/{applicationId}/reject")
    public ResponseEntity<String> rejectRental(@PathVariable Integer applicationId){
        ApplicationOfRental application = ownerService.rejectApplicationOfRental(applicationId);
        return ResponseEntity.ok("Rental application with ID " + applicationId + " has been rejected.");
    }

    @Secured("ROLE_OWNER")
    @PostMapping("/applications/view/{applicationId}/accept")
    public ResponseEntity<String> acceptViewing(@PathVariable Integer applicationId){
        ApplicationForView application = ownerService.acceptApplicationForView(applicationId);
        return ResponseEntity.ok("Viewing application with ID " + applicationId + " has been accepted.");
    }

    @Secured("ROLE_OWNER")
    @PostMapping("/applications/view/{applicationId}/reject")
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

}









