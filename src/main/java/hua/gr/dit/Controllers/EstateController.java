//package hua.ds_project.project.controllers;
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

    ArrayList<Estate> estates = new ArrayList<Estate>();

    @PostConstruct
    public void setup(){
        Estate estate1 = new Estate();
        estate1.setId(1);
        estate1.setSquareMeters(120);
        estate1.setTypeOfEstate("Apartment");
        estate1.setAddress("123 Main Street");
        estate1.setArea("Downtown");
        estate1.setAgeOfConstruction(10);
        estate1.setDuration(12);
        estate1.setPrice(1500.00f);
        estate1.setFloor("5th");
        estate1.setAmountOfRooms(3);
        estate1.setTypeOfHeating("Central");
        estate1.setParking(true);
        estate1.setAvailability(true);
        estate1.setDescription("Spacious and well-lit apartment.");
        estate1.setLastUpdated("2025-01-01");

        Estate estate2 = new Estate();
        estate2.setId(2);
        estate2.setSquareMeters(200);
        estate2.setTypeOfEstate("House");
        estate2.setAddress("456 Elm Street");
        estate2.setArea("Suburbs");
        estate2.setAgeOfConstruction(5);
        estate2.setDuration(24);
        estate2.setPrice(2500.00f);
        estate2.setFloor("Ground");
        estate2.setAmountOfRooms(5);
        estate2.setTypeOfHeating("Gas");
        estate2.setParking(true);
        estate2.setAvailability(false);
        estate2.setDescription("Modern house with a beautiful garden.");
        estate2.setLastUpdated("2025-01-02");

        Estate estate3 = new Estate();
        estate3.setId(3);
        estate3.setSquareMeters(80);
        estate3.setTypeOfEstate("Studio");
        estate3.setAddress("789 Pine Avenue");
        estate3.setArea("City Center");
        estate3.setAgeOfConstruction(15);
        estate3.setDuration(6);
        estate3.setPrice(1000.00f);
        estate3.setFloor("3rd");
        estate3.setAmountOfRooms(1);
        estate3.setTypeOfHeating("Electric");
        estate3.setParking(false);
        estate3.setAvailability(true);
        estate3.setDescription("Compact studio perfect for students.");
        estate3.setLastUpdated("2025-01-03");

        // Add these objects to a list or database
//       estateService.saveEstate(estate1);
//       estateService.saveEstate(estate2);
//       estateService.saveEstate(estate3);

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
//        estateService.saveEstate(e)
        return "addEstatePage";
    }
    @Secured("ROLE_ADMIN")
    @PostMapping("/new")
    public String saveStudent(@ModelAttribute("estate") Estate estate, Model model) {
        estateService.saveEstate(estate);
        model.addAttribute("estates", estates);
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
