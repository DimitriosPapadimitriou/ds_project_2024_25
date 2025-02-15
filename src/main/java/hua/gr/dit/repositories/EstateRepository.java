package hua.gr.dit.repositories;

import hua.gr.dit.Entitties.Estate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstateRepository extends JpaRepository<Estate, Integer> {

    List<Estate> findByAreaAndAmountOfRoomsAndPrice(String area, Integer amountOfRooms, float price);

    List<Estate> findByAddress(String address);

    List<Estate> findByArea(String area);

//    List<Estate> findByAgeOfConstruction(Integer ageOfConstruction);

    List<Estate> findByPrice(Float price);

    List<Estate> findByFloor(String floor);

    List<Estate> findByAmountOfRooms(Integer amountOfRooms);

    @Query("SELECT e FROM Estate e WHERE e.availability = true AND e.typeOfHeating = :typeOfHeating")
    List<Estate> findByTypeOfHeating(@Param("typeOfHeating") String typeOfHeating);

    @Query("SELECT e FROM Estate e WHERE e.availability = true AND e.parking = :parking")
    List<Estate> findByParking(Boolean parking);

    List<Estate> findByAvailability(Boolean availability);

    List<Estate> findByOwnerId(Integer ownerId);

    @Query("SELECT e FROM Estate e WHERE e.availability = true AND LOWER(e.area) LIKE LOWER(CONCAT('%', :area, '%'))")
    List<Estate> searchByArea(@Param("area") String area);

    @Query("SELECT e FROM Estate e WHERE e.availability = true AND LOWER(e.address) LIKE LOWER(CONCAT('%', :address, '%'))")
    List<Estate> searchByAddress(@Param("address") String address);

    @Query("SELECT e FROM Estate e WHERE e.availability = true AND (:minValue IS NULL OR e.squareMeters >= :minValue) AND (:maxValue IS NULL OR e.squareMeters < :maxValue)")
    List<Estate> findBySquareMetersRange(@Param("minValue") Integer minValue, @Param("maxValue") Integer maxValue);

    @Query("SELECT e FROM Estate e WHERE e.availability = true AND (:minValue IS NULL OR e.ageOfConstruction >= :minValue) AND (:maxValue IS NULL OR e.ageOfConstruction < :maxValue)")
    List<Estate> findByAgeOfConstruction(@Param("minValue") Integer minValue, @Param("maxValue") Integer maxValue);

    @Query("SELECT e FROM Estate e WHERE e.availability = true AND (:minValue IS NULL OR e.price >= :minValue) AND (:maxValue IS NULL OR e.price < :maxValue)")
    List<Estate> findByPriceRange(@Param("minValue") Integer minValue, @Param("maxValue") Integer maxValue);

}
