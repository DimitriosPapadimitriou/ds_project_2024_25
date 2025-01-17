package hua.gr.dit.Controllers;

import hua.gr.dit.Entitties.Role;
import hua.gr.dit.repositories.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class AuthController {

    @GetMapping()
    public String login() {
        return "login";
    }
}
