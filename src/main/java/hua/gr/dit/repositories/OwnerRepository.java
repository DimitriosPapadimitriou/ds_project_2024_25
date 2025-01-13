package hua.gr.dit.repositories;

import hua.gr.dit.Entitties.ApplicationForView;
import hua.gr.dit.Entitties.ApplicationOfRental;
import hua.gr.dit.Entitties.Estate;
import hua.gr.dit.Entitties.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Integer> {
}
