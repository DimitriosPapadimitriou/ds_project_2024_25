package hua.gr.dit.repositories;

import hua.gr.dit.Entitties.ApplicationForRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationForRegistrationRepository extends JpaRepository<ApplicationForRegistration, Integer> {

    List<ApplicationForRegistration> findByStatus(String status);
}
