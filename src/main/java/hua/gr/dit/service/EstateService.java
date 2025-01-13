package hua.gr.dit.service;

import hua.gr.dit.Entitties.Estate;
import hua.gr.dit.repositories.EstateRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstateService {

    private EstateRepository estateRepository;

    public EstateService(EstateRepository estateRepository) {
        this.estateRepository = estateRepository;
    }

    @Transactional
    public Estate saveEstate(Estate estate){
        return estateRepository.save(estate);
    }

    @Transactional
    public Estate getEstateById(Integer id){
        return estateRepository.findById(id).orElse(null);
    }

    @Transactional
    public List<Estate> getAllEstates(){
        return estateRepository.findAll();
    }

    @Transactional
    public void deleteEstateById(Integer id){
        estateRepository.deleteById(id);
    }

    @Transactional
    public List<Estate> getEstateByAddress(String address){
        return estateRepository.findByAddress(address);
    }

    @Transactional
    public List<Estate> getEstateByArea(String area){
        return estateRepository.findByArea(area);
    }

    @Transactional
    public List<Estate> getEstateByAge(Integer age){
        return estateRepository.findByAgeOfConstruction(age);
    }

    @Transactional
    public List<Estate> getEstateByPrice(Float price){
        return estateRepository.findByPrice(price);
    }

    @Transactional
    public List<Estate> getEstateByFloor(String floor){
        return estateRepository.findByFloor(floor);
    }

    @Transactional
    public List<Estate> getEstateByAmountOfRooms(Integer rooms){
        return estateRepository.findByAmountOfRooms(rooms);
    }

    @Transactional
    public List<Estate> getEstateByTypeOfHeating(String heating){
        return estateRepository.findByTypeOfHeating(heating);
    }

    @Transactional
    public List<Estate> getEstateByParking(Boolean parkingSpots){
        return estateRepository.findByParking(parkingSpots);
    }

    @Transactional
    public List<Estate> getEstateByAvailability(Boolean availability){
        return estateRepository.findByAvailability(availability);
    }


}
