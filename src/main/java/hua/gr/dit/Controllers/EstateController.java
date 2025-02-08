package hua.gr.dit.Controllers;

import hua.gr.dit.Entitties.Estate;
import hua.gr.dit.service.EstateService;
import jakarta.annotation.PostConstruct;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/estate")
public class EstateController {

    private EstateService estateService;

    public EstateController(EstateService estateService) {
        this.estateService = estateService;
    }



    @GetMapping()
    public String estatePage(Model model){
        model.addAttribute("estates", estateService.getAllEstates());
        return "EstatePage";
    }


    @GetMapping("/{id}")
    public String showEstate(@PathVariable int id, Model model){
        model.addAttribute("estates", estateService.getEstateById(id));
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

    @GetMapping("/search/area")
    public String getEstatesByArea(@PathVariable String area, Model model){
        model.addAttribute("estates", estateService.getEstateByArea(area));
        return "EstatePage";
    }

    @GetMapping("/search/address")
    public String getEstatesByAddress(@PathVariable String address, Model model){
        model.addAttribute("estates", estateService.getEstateByAddress(address));
        return "EstatePage";
    }

    @GetMapping("/search/age")
    public String getEstatesByAge(@PathVariable Integer age, Model model){
        model.addAttribute("estates", estateService.getEstateByAge(age));
        return "EstatePage";
    }

    @GetMapping("/search/price")
    public String getEstatesByPrice(@PathVariable Float price, Model model){
        model.addAttribute("estates", estateService.getEstateByPrice(price));
        return "EstatePage";
    }

    @GetMapping("/search/floor")
    public String getEstatesByFloor(@PathVariable String floor, Model model){
        model.addAttribute("estates", estateService.getEstateByFloor(floor));
        return "EstatePage";
    }

    @GetMapping("/search/rooms")
    public String getEstatesByRooms(@PathVariable Integer rooms, Model model){
        model.addAttribute("estates", estateService.getEstateByAmountOfRooms(rooms));
        return "EstatePage";
    }

    @GetMapping("/search/heating")
    public String getEstatesByHeating(@PathVariable String heating, Model model){
        model.addAttribute("estates", estateService.getEstateByTypeOfHeating(heating));
        return "EstatePage";
    }

    @GetMapping("/search/parking")
    public String getEstatesByArea(@PathVariable Boolean parking, Model model){
        model.addAttribute("estates", estateService.getEstateByParking(parking));
        return "EstatePage";
    }

    @GetMapping("/search/availability")
    public String getEstatesByAvailability(@PathVariable Boolean availability, Model model){
        model.addAttribute("estates", estateService.getEstateByAvailability(availability));
        return "EstatePage";
    }





}
