package hua.gr.dit.Controllers;

import hua.gr.dit.Entitties.ApplicationForRegistration;
import hua.gr.dit.Entitties.ApplicationForView;
import hua.gr.dit.Entitties.ApplicationOfRental;
import hua.gr.dit.Entitties.Estate;
import hua.gr.dit.service.AdminService;
import hua.gr.dit.service.OwnerService;
import hua.gr.dit.service.TenantService;
import jakarta.annotation.PostConstruct;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("/applications")
public class ApplicationController {

    private OwnerService ownerService;
    private TenantService tenantService;
    private AdminService adminService;

    public ApplicationController(OwnerService ownerService, TenantService tenantService, AdminService adminService) {
        this.ownerService = ownerService;
        this.tenantService = tenantService;
        this.adminService = adminService;
    }

    @GetMapping()
    public String application(){
        return "ApplicationPage";
    }

    @Secured("ROLE_OWNER")
    @GetMapping("/registration/new")
    public String addApplicationForRegistration(Model model, Integer adminId, Integer ownerId, Estate estate){
        model.addAttribute("application", ownerService.submitApplicationForRegistration(adminId, ownerId, estate));
        return "AddApplicationForRegistrationPage";
    }

    @Secured("ROLE_OWNER")
    @PostMapping("/registration/new")
    public String saveApplicationForRegistration(Model model, Integer adminId, Integer ownerId, Estate estate){
        model.addAttribute("application", ownerService.submitApplicationForRegistration(adminId, ownerId, estate));
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
    @GetMapping("/view")
    public String applicationForView(Model model){
        ApplicationForView view = new ApplicationForView();
        model.addAttribute("application", tenantService.submitViewingApplication(view));
        return "ApplicationForRentalPage";
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









