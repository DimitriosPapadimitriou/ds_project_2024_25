package hua.gr.dit.repositories;

import hua.gr.dit.Entitties.ApplicationOfRental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationOfRentalRepository extends JpaRepository<ApplicationOfRental, Integer> {
}
