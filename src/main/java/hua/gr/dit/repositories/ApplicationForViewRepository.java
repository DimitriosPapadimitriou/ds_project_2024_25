package hua.gr.dit.repositories;

import hua.gr.dit.Entitties.ApplicationForView;
import hua.gr.dit.Entitties.ApplicationOfRental;
import hua.gr.dit.Entitties.Estate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationForViewRepository extends JpaRepository<ApplicationForView, Integer> {
    List<ApplicationForView> findViewApplicationsByEstateId(Integer estateId);

    List<ApplicationForView> findByEstateAndStatus(Estate estate, String status);


}
