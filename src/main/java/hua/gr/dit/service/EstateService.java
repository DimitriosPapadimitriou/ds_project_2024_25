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
        return estateRepository.searchByAddress(address);
    }

    @Transactional
    public List<Estate> getEstateBySquareMeters(Integer minValue, Integer maxValue){
        return estateRepository.findBySquareMetersRange(minValue,maxValue);
    }


    @Transactional
    public List<Estate> getEstatesByArea(String area) {
        return estateRepository.searchByArea(area); // Use case-insensitive search
    }

    @Transactional
    public List<Estate> getEstateByAge(Integer minValue, Integer maxValue){
        return estateRepository.findByAgeOfConstruction(minValue,maxValue);
    }

    @Transactional
    public List<Estate> getEstateByPrice(Integer minValue, Integer maxValue){
        return estateRepository.findByPriceRange(minValue,maxValue);
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

    @Transactional
    public List<Estate> getEstateByOwnerId(Integer OwnerId){
        return estateRepository.findByOwnerId(OwnerId); // find by onwer Id
    }

}
