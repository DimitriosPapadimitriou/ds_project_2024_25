package hua.gr.dit.repositories;

import hua.gr.dit.Entitties.Role;
import hua.gr.dit.Entitties.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String roleName);

    default Role updateOrInsert(Role role) {
        Role existing_role = findByName(role.getName()).orElse(null);
        if (existing_role != null) {
            return existing_role;
        }
        else {
            return save(role);
        }
    }


}
