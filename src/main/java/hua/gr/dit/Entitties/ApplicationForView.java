//package hua.ds_project.project.entities;
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

    @Column
    private Integer tenantID;

    @Column
    private Integer ownerID;

    public ApplicationForView(Estate estate, Tenant tenant, Integer applicationID, String status, String date, String description, String dateOfVisit, Integer tenantID, Integer ownerID) {
        this.estate = estate;
        this.tenant = tenant;
        this.applicationID = applicationID;
        this.status = status;
        this.date = date;
        this.description = description;
        this.dateOfVisit = dateOfVisit;
        this.tenantID = tenantID;
        this.ownerID = ownerID;
    }

    public ApplicationForView() {
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

    public String getDateOfVisit() {
        return dateOfVisit;
    }

    public void setDateOfVisit(String dateOfVisit) {
        this.dateOfVisit = dateOfVisit;
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
        return "ApplicationForView{" +
                "applicationID=" + applicationID +
                ", date='" + date + '\'' +
                ", description='" + description + '\'' +
                ", dateOfVisit='" + dateOfVisit + '\'' +
                ", tenantID=" + tenantID +
                ", ownerID=" + ownerID +
                '}';
    }
}
