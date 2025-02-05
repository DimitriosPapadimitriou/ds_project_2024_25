package hua.gr.dit.Entitties;
import jakarta.persistence.*;

@Entity
@Table(name = "application_for_registration")
public class ApplicationForRegistration {

//    @OneToOne
//    @JoinColumn(name = "estate_id")
//    private Estate estate;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer applicationID;

    @Column
    private String documents;

    @Column
    private String properties;

    @Column
    private Integer squareMeters;

    @Column
    private String typeOfEstate;

    @Column
    private String address;

    @Column
    private String area;

    @Column
    private Integer ageOfConstruction;

    @Column
    private Integer duration;

    @Column
    private Integer price;

    @Column
    private String floor;

    @Column
    private Integer amountOfRooms;

    @Column
    private String typeOfHeating;

    @Column
    private Boolean parking;

    @Column
    private String description;

    @Column
    private String status;


    public ApplicationForRegistration(String address, Admin admin, Integer ageOfConstruction, Integer amountOfRooms, Integer applicationID, String area, String description, String documents, Integer duration, String floor, Owner owner, Boolean parking, Integer price, String properties, Integer squareMeters, String status, String typeOfEstate, String typeOfHeating) {
        this.address = address;
        this.admin = admin;
        this.ageOfConstruction = ageOfConstruction;
        this.amountOfRooms = amountOfRooms;
        this.applicationID = applicationID;
        this.area = area;
        this.description = description;
        this.documents = documents;
        this.duration = duration;
        this.floor = floor;
        this.owner = owner;
        this.parking = parking;
        this.price = price;
        this.properties = properties;
        this.squareMeters = squareMeters;
        this.status = status;
        this.typeOfEstate = typeOfEstate;
        this.typeOfHeating = typeOfHeating;
    }

    public ApplicationForRegistration() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }


    public Integer getApplicationID() {
        return applicationID;
    }

    public void setApplicationID(Integer applicationID) {
        this.applicationID = applicationID;
    }

    public String getDocuments() {
        return documents;
    }

    public void setDocuments(String documents) {
        this.documents = documents;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    public Integer getSquareMeters() {
        return squareMeters;
    }

    public void setSquareMeters(Integer squareMeters) {
        this.squareMeters = squareMeters;
    }

    public String getTypeOfEstate() {
        return typeOfEstate;
    }

    public void setTypeOfEstate(String typeOfEstate) {
        this.typeOfEstate = typeOfEstate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Integer getAgeOfConstruction() {
        return ageOfConstruction;
    }

    public void setAgeOfConstruction(Integer ageOfConstruction) {
        this.ageOfConstruction = ageOfConstruction;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public Integer getAmountOfRooms() {
        return amountOfRooms;
    }

    public void setAmountOfRooms(Integer amountOfRooms) {
        this.amountOfRooms = amountOfRooms;
    }

    public String getTypeOfHeating() {
        return typeOfHeating;
    }

    public void setTypeOfHeating(String typeOfHeating) {
        this.typeOfHeating = typeOfHeating;
    }

    public Boolean getParking() {
        return parking;
    }

    public void setParking(Boolean parking) {
        this.parking = parking;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
