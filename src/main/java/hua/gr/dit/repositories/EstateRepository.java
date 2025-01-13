package hua.gr.dit.repositories;

import hua.gr.dit.Entitties.Estate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstateRepository extends JpaRepository<Estate, Integer> {

    List<Estate> findByAreaAndAmountOfRoomsAndPrice(String area, Integer amountOfRooms, float price);

    List<Estate> findByAddress(String address);

    List<Estate> findByArea(String area);

    List<Estate> findByAgeOfConstruction(Integer ageOfConstruction);

    List<Estate> findByPrice(Float price);

    List<Estate> findByFloor(String floor);

    List<Estate> findByAmountOfRooms(Integer amountOfRooms);

    List<Estate> findByTypeOfHeating(String typeOfHeating);

    List<Estate> findByParking(Boolean parking);

    List<Estate> findByAvailability(Boolean availability);

}
