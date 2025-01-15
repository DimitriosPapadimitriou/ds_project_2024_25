package hua.gr.dit.repositories;

import hua.gr.dit.Entitties.ApplicationForView;
import hua.gr.dit.Entitties.ApplicationOfRental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationOfRentalRepository extends JpaRepository<ApplicationOfRental, Integer> {
    List<ApplicationOfRental> findRentalApplicationsByOwnerId(Integer ownerId);

    List<ApplicationForView> findViewingApplicationsByOwnerId(Integer ownerId);

}
