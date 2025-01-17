package hua.gr.dit.Entitties;
import jakarta.persistence.*;

@Entity
@Table
public class ApplicationForRegistration {

    @OneToOne
    @JoinColumn(name = "estate_id")
    private Estate estate;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @Column
    private String status;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer applicationID;

    @Column
    private String documents;

    @Column
    private String date;

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


    public ApplicationForRegistration(Estate estate, Admin admin, Owner owner, String status, Integer applicationID, String documents, String date, String properties, Integer squareMeters, String typeOfEstate, String address, String area, Integer ageOfConstruction, Integer duration, Integer price, String floor, Integer amountOfRooms, String typeOfHeating, Boolean parking, String description) {
        this.estate = estate;
        this.admin = admin;
        this.owner = owner;
        this.status = status;
        this.applicationID = applicationID;
        this.documents = documents;
        this.date = date;
        this.properties = properties;
        this.squareMeters = squareMeters;
        this.typeOfEstate = typeOfEstate;
        this.address = address;
        this.area = area;
        this.ageOfConstruction = ageOfConstruction;
        this.duration = duration;
        this.price = price;
        this.floor = floor;
        this.amountOfRooms = amountOfRooms;
        this.typeOfHeating = typeOfHeating;
        this.parking = parking;
        this.description = description;
    }

    public ApplicationForRegistration() {
    }

    public Estate getEstate() {
        return estate;
    }

    public void setEstate(Estate estate) {
        this.estate = estate;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    @Override
    public String toString() {
        return "ApplicationForRegistration{" +
                "applicationID=" + applicationID +
                ", documents='" + documents + '\'' +
                ", date='" + date + '\'' +
                ", properties='" + properties + '\'' +
                ", squareMeters=" + squareMeters +
                ", typeOfEstate='" + typeOfEstate + '\'' +
                ", address='" + address + '\'' +
                ", area='" + area + '\'' +
                ", ageOfConstruction=" + ageOfConstruction +
                ", duration=" + duration +
                ", price=" + price +
                ", floor='" + floor + '\'' +
                ", amountOfRooms=" + amountOfRooms +
                ", typeOfHeating='" + typeOfHeating + '\'' +
                ", parking=" + parking +
                ", description='" + description + '\'' +
                '}';
    }
}
