package hua.gr.dit.Entitties;
import jakarta.persistence.*;

@Entity
@Table
public class ApplicationForView {

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "estate_id")
    private Estate estate;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

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
    private String dateOfVisit;

    public ApplicationForView(Tenant tenant, String status, Estate estate, String description, String dateOfVisit, String date, Integer applicationID) {
        this.tenant = tenant;
        this.status = status;
        this.estate = estate;
        this.description = description;
        this.dateOfVisit = dateOfVisit;
        this.date = date;
        this.applicationID = applicationID;
    }

    public ApplicationForView() {
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

    public String getDateOfVisit() {
        return dateOfVisit;
    }

    public void setDateOfVisit(String dateOfVisit) {
        this.dateOfVisit = dateOfVisit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Estate getEstate() {
        return estate;
    }

    public void setEstate(Estate estate) {
        this.estate = estate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }
}
