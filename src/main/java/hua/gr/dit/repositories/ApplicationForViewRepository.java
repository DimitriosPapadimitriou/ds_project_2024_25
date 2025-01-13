package hua.gr.dit.repositories;

import hua.gr.dit.Entitties.ApplicationForView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationForViewRepository extends JpaRepository<ApplicationForView, Integer> {
}
