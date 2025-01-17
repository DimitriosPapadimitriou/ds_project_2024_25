package hua.gr.dit.Entitties;
import jakarta.persistence.*;

@Entity
@Table
public class ApplicationOfRental {

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "estate_id")
    private Estate estate;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer applicationID;

    @Column
    private String status;

    @Column
    private String date;

    @Column
    private String description;

    @Column
    private String duration;

    @Column
    private Integer rent;

    @Column
    private Integer tenantID;

    @Column
    private Integer ownerID;

    public ApplicationOfRental(Estate estate, Tenant tenant, Owner owner, Integer applicationID, String status, String date, String description, String duration, Integer rent, Integer tenantID, Integer ownerID) {
        this.estate = estate;
        this.tenant = tenant;
        this.owner = owner;
        this.applicationID = applicationID;
        this.status = status;
        this.date = date;
        this.description = description;
        this.duration = duration;
        this.rent = rent;
        this.tenantID = tenantID;
        this.ownerID = ownerID;
    }

    public ApplicationOfRental() {
    }

    public Estate getEstate() {
        return estate;
    }

    public void setEstate(Estate estate) {
        this.estate = estate;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Integer getRent() {
        return rent;
    }

    public void setRent(Integer rent) {
        this.rent = rent;
    }

    public Integer getTenantID() {
        return tenantID;
    }

    public void setTenantID(Integer tenantID) {
        this.tenantID = tenantID;
    }

    public Integer getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(Integer ownerID) {
        this.ownerID = ownerID;
    }

    @Override
    public String toString() {
        return "ApplicationOfRental{" +
                "applicationID=" + applicationID +
                ", date='" + date + '\'' +
                ", description='" + description + '\'' +
                ", duration='" + duration + '\'' +
                ", rent=" + rent +
                ", tenantID=" + tenantID +
                ", ownerID=" + ownerID +
                '}';
    }
}
