package hua.gr.dit.Controllers;

import hua.gr.dit.Entitties.Owner;
import hua.gr.dit.Entitties.User;
import hua.gr.dit.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    private UserRepository userRepository;

    public HomeController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping()
    public String homePage(Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);

        if(user.getOwner() != null) {
            Owner owner = user.getOwner();
            model.addAttribute("ownerId", owner.getId());
        } else {
            model.addAttribute("ownerId", 0);
        }
        return "index";
    }
}
