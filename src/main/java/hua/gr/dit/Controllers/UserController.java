package hua.gr.dit.Controllers;

import hua.gr.dit.Entitties.Owner;
import hua.gr.dit.Entitties.Role;
import hua.gr.dit.Entitties.Tenant;
import hua.gr.dit.Entitties.User;
import hua.gr.dit.repositories.OwnerRepository;
import hua.gr.dit.repositories.RoleRepository;
import hua.gr.dit.repositories.TenantRepository;
import hua.gr.dit.service.OwnerService;
import hua.gr.dit.service.TenantService;
import hua.gr.dit.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {


    private UserService userService;

    private RoleRepository roleRepository;
    private TenantRepository tenantRepository;
    private OwnerRepository ownerService;

    public UserController(OwnerRepository ownerService, RoleRepository roleRepository, TenantRepository tenantRepository, UserService userService) {
        this.ownerService = ownerService;
        this.roleRepository = roleRepository;
        this.tenantRepository = tenantRepository;
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }


    @PostMapping("/register")
    public String saveUser(@ModelAttribute User user, @RequestParam(required = false) String roleName , Model model){
        System.out.println("Roles: "+user.getRoles());
        Integer id = userService.saveUser(user, roleName);
        if("ROLE_TENANT".equals(roleName)){
            Tenant tenant = new Tenant();
            tenant.setUser(user);
            tenantRepository.save(tenant);
        }
        if("ROLE_OWNER".equals(roleName)){
            Owner owner = new Owner();
            owner.setUser(user);
            ownerService.save(owner);
        }
        String message = "User '"+id+"' saved successfully !";
        model.addAttribute("msg", message);
        return "Thank-you-page";
    }

    @GetMapping("/users")
    public String showUsers(Model model){
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("roles", roleRepository.findAll());
        return "auth/users";
    }

    @GetMapping("/user/{user_id}")
    public String showUser(@PathVariable Integer     user_id, Model model){
        model.addAttribute("user", userService.getUser(user_id));
        return "auth/user";
    }

    @GetMapping("/user/role/delete/{user_id}/{role_id}")
    public String deleteRolefromUser(@PathVariable Integer user_id, @PathVariable Integer role_id, Model model){
        User user = (User) userService.getUser(user_id);
        Role role = roleRepository.findById(role_id).get();
        user.getRoles().remove(role);
        System.out.println("Roles: "+user.getRoles());
        userService.updateUser(user);
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("roles", roleRepository.findAll());
        return "auth/users";

    }

    @GetMapping("/user/role/add/{user_id}/{role_id}")
    public String addRoletoUser(@PathVariable Integer user_id, @PathVariable Integer role_id, Model model){
        User user = (User) userService.getUser(user_id);
        Role role = roleRepository.findById(role_id).get();
        user.getRoles().add(role);
        System.out.println("Roles: "+user.getRoles());
        userService.updateUser(user);
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("roles", roleRepository.findAll());
        return "auth/users";

    }

}
