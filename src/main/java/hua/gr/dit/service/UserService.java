package hua.gr.dit.service;

import hua.gr.dit.Entitties.Role;
import hua.gr.dit.Entitties.Tenant;
import hua.gr.dit.Entitties.User;
import hua.gr.dit.repositories.RoleRepository;
import hua.gr.dit.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {


    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Tenant getTenantByUsername(String username) {
        System.out.println("DEBUG: Searching for username: " + username);
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("No user found with username: " + username))
                .getTenant();
    }

    @Transactional
    public Integer saveUser(User user, String roleName) {
        String passwd= user.getPassword();
        String encodedPassword = passwordEncoder.encode(passwd);
        user.setPassword(encodedPassword);

        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        user = userRepository.save(user);// user by default role
        return user.getId();
    }

    @Transactional
    public Integer updateUser(User user) {
        user = userRepository.save(user);
        return user.getId();
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> opt = userRepository.findByUsername(username);

        if(opt.isEmpty())
            throw new UsernameNotFoundException("User with email: " +username +" not found !");
        else {
            User user = opt.get();
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    user.getRoles()
                            .stream()
                            .map(role-> new SimpleGrantedAuthority(role.toString()))
                            .collect(Collectors.toSet())
            );
        }
    }

    @Transactional
    public Object getUsers() {
        return userRepository.findAll();
    }


    @Transactional
    public Object getUser(Integer userId) {
        return userRepository.findById(userId).get();
    }

    @Transactional
    public void updateOrInsertRole(Role role) {
        roleRepository.updateOrInsert(role);
    }

}
