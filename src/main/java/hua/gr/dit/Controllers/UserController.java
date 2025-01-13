package hua.gr.dit.Controllers;

import hua.gr.dit.Entitties.Role;
import hua.gr.dit.Entitties.User;
import hua.gr.dit.repositories.RoleRepository;
import hua.gr.dit.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {


    private UserService userService;

    private RoleRepository roleRepository;

    public UserController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
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
        Integer id = userService.saveUser(user, roleName); //Edw allaksa thn sunarthsh na pairnei kai ena role Name gia ton rolo pou theloume
        String message = "User '"+id+"' saved successfully !";
        model.addAttribute("msg", message);
        return "index";
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

//    @PostMapping("/user/{user_id}")
//    public String saveStudent(@PathVariable Integer user_id, @ModelAttribute("user") User user, Model model) {
//        User the_user = (User) userService.getUser(user_id);
//        the_user.setEmail(user.getEmail());
//        the_user.setUserName(user.getUserName());
//        userService.updateUser(the_user);
//        model.addAttribute("users", userService.getUsers());
//        return "auth/users";
//    }

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
