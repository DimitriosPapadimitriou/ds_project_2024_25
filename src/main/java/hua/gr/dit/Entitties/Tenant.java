package hua.gr.dit.Entitties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Tenant {

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL)
    private List<ApplicationForView> views = new ArrayList<>();

    @OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL)
    private List<ApplicationOfRental> rentals = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;


    public Tenant(Integer id, List<ApplicationOfRental> rentals, User user, List<ApplicationForView> views) {
        this.id = id;
        this.rentals = rentals;
        this.user = user;
        this.views = views;
    }

    public Tenant() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Tenant{" +
                "id=" + id +
                ", user=" + user +
                ", views=" + views +
                ", rentals=" + rentals +
                '}';
    }
}
