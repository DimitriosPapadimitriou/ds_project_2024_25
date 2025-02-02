package hua.gr.dit.repositories;

import hua.gr.dit.Entitties.Estate;
import hua.gr.dit.Entitties.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Integer> {
}
