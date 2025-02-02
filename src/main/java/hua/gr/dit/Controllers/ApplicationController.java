package hua.gr.dit.Controllers;

import hua.gr.dit.Entitties.*;
import hua.gr.dit.Entitties.User;
import hua.gr.dit.repositories.UserRepository;
import hua.gr.dit.service.AdminService;
import hua.gr.dit.service.OwnerService;
import hua.gr.dit.service.TenantService;
import hua.gr.dit.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/applications")
public class ApplicationController {

    private OwnerService ownerService;
    private TenantService tenantService;
    private AdminService adminService;
    private UserService userService;

    public ApplicationController(AdminService adminService, OwnerService ownerService, TenantService tenantService, UserService userService) {
        this.adminService = adminService;
        this.ownerService = ownerService;
        this.tenantService = tenantService;
        this.userService = userService;
    }

    @GetMapping()
    public String application(){
        return "ApplicationPage";
    }

    @Secured("ROLE_OWNER")
    @GetMapping("/registrationnew")
    public String addApplicationForRegistration(Model model){
        model.addAttribute("application", new ApplicationForRegistration());
        return "AddApplicationForRegistrationPage";
    }

    @Secured("ROLE_OWNER")
    @PostMapping("/registration/new")
    public String saveApplicationForRegistration(Model model, @ModelAttribute("ApplicationForRegistration") ApplicationForRegistration applicationForRegistration){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Assuming username is unique

//         Retrieve the full user entity (assuming you have a UserService)
//        Optional<User> loggedInUser = userRepository.findByUsername(username);
//        User user = loggedInUser.get();
//        Integer ownerId = user.getOwner().getId();

        Estate estate = new Estate(
                15,                    // id
                10,                    // squareMeters
                "estate",              // typeOfEstate
                "address",             // address
                "area",                // area
                123,                   // ageOfConstruction
                123,                   // duration
                123.0f,                // price (as Float)
                "1st floor",           // floor (as String)
                3,                     // amountOfRooms
                "heat",                // typeOfHeating
                false,                 // parking
                true,                  // availability
                "wefwef",              // description
                "wtwf"                 // lastUpdated
        );
        ownerService.submitApplicationForRegistration(9, 1, estate);
        return "AddApplicationForRegistrationPage";
    }



    @Secured("ROLE_OWNER")
    @GetMapping("/registration/{id}")
    public String applicationForRegistration(Model model, Integer adminId, Integer ownerId, Estate estate){
        model.addAttribute("application", ownerService.submitApplicationForRegistration(adminId, ownerId, estate));
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









