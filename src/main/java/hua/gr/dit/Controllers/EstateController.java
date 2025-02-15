package hua.gr.dit.Controllers;

import hua.gr.dit.Entitties.Estate;
import hua.gr.dit.Entitties.Owner;
import hua.gr.dit.Entitties.User;
import hua.gr.dit.repositories.UserRepository;
import hua.gr.dit.service.EstateService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.Id;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/estate")
public class EstateController {

    private EstateService estateService;
    private UserRepository userRepository;

    public EstateController(EstateService estateService, UserRepository userRepository) {
        this.estateService = estateService;
        this.userRepository = userRepository;
    }

    @GetMapping("/{estateID}")
    public String showEstate(@PathVariable int estateID,
                             @RequestParam(required = false) String address,
                             @RequestParam(required = false) String area,
                             @RequestParam(required = false) String size,
                             @RequestParam(required = false) String decade,
                             @RequestParam(required = false) String price,
                             @RequestParam(required = false) String heating,
                             @RequestParam(required = false) String parking,

                             Model model){
        System.out.println("the value of address is " + address);
        List<Estate> estates;

        if(address != null && !address.isEmpty()){
            estates = estateService.getEstateByAddress(address.trim());
        }
        else if(area != null && !area.isEmpty()) {
            estates = estateService.getEstatesByArea(area.trim());
        }
        else if(size != null && !size.isEmpty()) {
            if(size.equals("Big")){
                estates = estateService.getEstateBySquareMeters(100,1000);
            } else if (size.equals("Medium")) {
                estates = estateService.getEstateBySquareMeters(50,100);
            } else if (size.equals("Small")) {
                estates = estateService.getEstateBySquareMeters(30,50);
            }else {
                estates = estateService.getEstateByAvailability(true);
            }
        }
        else if (decade != null && !decade.isEmpty()) {
            if(decade.equals("20")){
                estates = estateService.getEstateByAge(2020,2030);
            } else if (decade.equals("10")) {
                estates = estateService.getEstateByAge(2010,2020);
            } else if (decade.equals("00")) {
                estates = estateService.getEstateByAge(2000,2010);
            } else if (decade.equals("90")) {
                estates = estateService.getEstateByAge(1990,2000);
            } else if (decade.equals("80")) {
                estates = estateService.getEstateByAge(1980,1990);
            } else if (decade.equals("70")) {
                estates = estateService.getEstateByAge(1970,1980);
            } else {
                estates = estateService.getEstateByAvailability(true);
            }

        }
        else if (price != null && !price.isEmpty()) {
            if(price.equals("greater20000")){
                estates = estateService.getEstateByPrice(20000,100000000);
            } else if (price.equals("greater10000")) {
                estates = estateService.getEstateByPrice(10000,20000);
            }else if (price.equals("greater5000")) {
                estates = estateService.getEstateByPrice(5000,10000);
            }else if (price.equals("Other")) {
                estates = estateService.getEstateByPrice(0,5000);
            } else{
                estates = estateService.getEstateByAvailability(true);

            }
        }
        else if (heating != null && !heating.isEmpty()) {
            estates = estateService.getEstateByTypeOfHeating(heating);
        }
        else if(parking != null && !parking.isEmpty()){
            if(parking.equals("true")) {
                estates = estateService.getEstateByParking(true);
            } else if(parking.equals("false")) {
                estates = estateService.getEstateByParking(false);
            } else {
                estates = estateService.getEstateByAvailability(true);
            }
        }
        else {
            estates = estateService.getEstateByAvailability(true);
        }

        model.addAttribute("estates", estates);
        model.addAttribute("myEstates", estateService.getEstateByOwnerId(estateID));

        return "EstatePage";
    }


    @Secured("ROLE_OWNER")
    @PostMapping("/isAvailable/{estateID}")
    public String showEstate(@PathVariable int estateID, Model model) {
        Estate estate = estateService.getEstateById(estateID);
        estate.setAvailability(true);
        estateService.saveEstate(estate);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email);
        Integer ownerId;
        if(user.getOwner() != null) {
            Owner owner = user.getOwner();
            ownerId = owner.getId();
        } else {
            Owner owner = user.getOwner();
            ownerId = 0;
        }
        return "redirect:/estate/" + ownerId;
    }




    @GetMapping("/details/estateId")
    public String estatePage(Model model){
        model.addAttribute("estates", estateService.getAllEstates());


//        model.addAttribute("myEstates", estateService.getEstateByOwnerId());
        return "EstatePage";
    }


    @Secured("ROLE_ADMIN") //xreiazetai to @EnableMethodSecurity(securedEnabled = true) sto config
    @GetMapping("/new")
    public String addEstatePage(Model model){
        Estate estate = new Estate();
        model.addAttribute("estate",estate);
        estateService.saveEstate(estate);
        return "addEstatePage";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/new")
    public String saveEstate(@ModelAttribute("estate") Estate estate, Model model) {
        estateService.saveEstate(estate);
        model.addAttribute("estates", estate);
        return "addEstatePage";
    }

    @Secured("ROLE_ADMIN") //xreiazetai to @EnableMethodSecurity(securedEnabled = true) sto config
    @DeleteMapping("/{id}")
    public String deleteEstateById(@PathVariable Integer id){
        estateService.deleteEstateById(id);
        return "addEstatePage";
    }

}
